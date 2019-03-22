package server.networking;

import global.NetworkingSettings;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

abstract class Server extends Thread{
    private Selector selector;
    private ServerSocketChannel serverSocket;
    private boolean stop = false;
    private List<String> pendingStrings;

    Server(int port) throws IOException {
        // Get selector
        this.selector = Selector.open();
        System.out.println("Selector open: " + selector.isOpen());
        // Get sendingServer socket channel and register with selector
        this.serverSocket = ServerSocketChannel.open();
        InetSocketAddress hostAddress = new InetSocketAddress(port);
        serverSocket.bind(hostAddress);
        serverSocket.configureBlocking(false);
        int ops = serverSocket.validOps();
        serverSocket.register(selector, ops, null);
        this.pendingStrings = new ArrayList<>();
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                update();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() throws IOException {
        selector.select();

        Set selectedKeys = selector.selectedKeys();
        Iterator iter = selectedKeys.iterator();

        while (iter.hasNext()) {

            SelectionKey ky = (SelectionKey) iter.next();
            if (ky.isAcceptable() && server.runnables.Server.isConnectedToDatabase()) {
                acceptClient();
            }
            else if (ky.isReadable()) {
                try {
                    readDataFromClient(ky);
                } catch (IOException ignored) {}
            }
            iter.remove();
        }
    }

    private void acceptClient() throws IOException {
        SocketChannel client = serverSocket.accept();
        client.configureBlocking(false);
        // Add the new connection to the selector
        client.register(selector, SelectionKey.OP_READ);
        System.out.println("Accepted new connection from client: " + client);
    }

    private void readDataFromClient(SelectionKey ky) throws IOException {
        // Read the data from client

        SocketChannel client = (SocketChannel) ky.channel();
        ByteBuffer buffer = ByteBuffer.allocate(NetworkingSettings.MAX_PACKET_SIZE);
        client.read(buffer);
        String output = new String(buffer.array()).trim();

        if (output.equals(NetworkingSettings.DISCONNECT_STRING)) {
            System.out.println("client from " +  client +"disconnected");
            client.close();
            return;
        }

        System.out.println("Message read from client: " + output);

        pendingStrings.add(output);

        if (output.equals(NetworkingSettings.END_OF_SERVER)) {
            client.close();
            cleanUp();
            System.out.println("Client messages are complete; close.");
        } else {
            handleOutput(output, client);
        }
    }

    abstract void handleOutput(String output, SocketChannel client);

    void writeToClient(String message, SocketChannel client) throws IOException {
        byte[] messageBytes = message.getBytes();
        ByteBuffer bufferToSend = ByteBuffer.wrap(messageBytes);
        client.write(bufferToSend);
        bufferToSend.clear();
    }

    public void cleanUp() throws IOException {
        stop = true;
        selector.close();
    }

    public List<String> getPendingStrings() {
        List<String> ret = pendingStrings;
        pendingStrings = new ArrayList<>();
        return ret;
    }
}
