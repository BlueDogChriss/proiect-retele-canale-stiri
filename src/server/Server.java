package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import common.NewsChanel;
import common.Transport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.net.Socket;


public class Server implements AutoCloseable {

    List<NewsChanel> listaCanale = new ArrayList<>();
    NewsChanel canal1 = new NewsChanel("c1", "d1");
    NewsChanel canal2 = new NewsChanel("c2", "d2");
    NewsChanel canal3 = new NewsChanel("c3", "d3");

    List<String> words = Arrays.asList("INJURII", "CRYPTO", "STIRI FALSE", "INJURATURI", "MANELE");

    private ServerSocket serverSocket;

    private ExecutorService executorService;

    @Override
    public void close() throws Exception {
        stop();

    }

    public void start(int port) throws IOException {
        stop();
        serverSocket = new ServerSocket(port);
        executorService = Executors.newFixedThreadPool(50 * Runtime.getRuntime().availableProcessors());
        final List<Socket> clients = Collections.synchronizedList(new ArrayList<Socket>());
        listaCanale.add(canal1);
        canal1.addStiri("s1");
        canal1.addStiri("s2");
        listaCanale.add(canal2);
        listaCanale.add(canal3);

        executorService.execute(() -> {
            while (serverSocket != null && !serverSocket.isClosed()) {
                try {
                    final Socket socket = serverSocket.accept();
                    executorService.submit(() -> {
                        try {
                            clients.add(socket);

                            while (socket != null) {

                                try {
                                    String message = Transport.receive(socket);

                                    if (message.equals("1")) {
                                        for (NewsChanel newsChanel : listaCanale) {
                                            Transport.send(newsChanel.toString(), socket);
                                        }
                                    }

                                    if (message.equals("2")) {
                                        Transport.send("NUME:", socket);
                                        String nume = Transport.receive(socket);
                                        Transport.send(nume, socket);
                                        Transport.send("DESCRIERE:", socket);
                                        String desc = Transport.receive(socket);
                                        Transport.send(desc, socket);
                                        NewsChanel c = new NewsChanel(nume, desc, socket);
                                        listaCanale.add(c);

                                        clients.forEach(client -> {
                                            try {
                                                Transport.send("A fost adaugat un nou canal!", client);
                                            } catch (Exception ignored) {

                                            }
                                        });
                                    }

                                    if (message.equals("3")) {
                                        boolean aux = false;
                                        for (NewsChanel newsChanel : listaCanale) {
                                            if (newsChanel.getCreatorCanal() == socket) {
                                                aux = true;
                                                break;
                                            }
                                        }
                                        if (!aux) {
                                            Transport.send("NU SUNTETI ABONAT LA NICIUN CANAL!", socket);
                                        } else {
                                            Transport.send("Nume:", socket);
                                            String nume = Transport.receive(socket);
                                            for (int i = 0; i < listaCanale.size(); i++) {
                                                if (listaCanale.get(i).getNume().equals(nume))
                                                    listaCanale.remove(i);
                                            }

                                            clients.forEach(client -> {
                                                try {
                                                    Transport.send("Canalul a fost sters", client);
                                                } catch (Exception ignored) {
                                                }
                                            });
                                        }
                                    }
                                    if (message.equals("4")) {
                                        Transport.send("Nume:", socket);
                                        String name = Transport.receive(socket);
                                        Transport.send(name, socket);
                                        for (NewsChanel newsChanel : listaCanale) {
                                            if (newsChanel.getNume().equals(name))
                                                newsChanel.addAbonati(socket);
                                        }
                                    }
                                    if (message.equals("5")) {
                                        boolean aux = false;
                                        for (NewsChanel newsChanel : listaCanale) {
                                            if (newsChanel.getCreatorCanal() == socket) {
                                                aux = true;
                                                break;
                                            }
                                        }
                                        if (!aux) {
                                            Transport.send("NU AVETI CANALE", socket);
                                        } else {
                                            Transport.send("Stiri:", socket);
                                            String news = Transport.receive(socket);
                                            Transport.send("Nume Canal:", socket);
                                            String name = Transport.receive(socket);
                                            for (NewsChanel newsChanel : listaCanale) {
                                                if (newsChanel.getNume().equals(name)) {
                                                    newsChanel.addStiri(news);
                                                    boolean ok = true;
                                                    for (String word : words) {
                                                        if (news.contains(word)) {
                                                            ok = false;
                                                            break;
                                                        }
                                                    }
                                                    if (ok) {
                                                        newsChanel.getClientiAbonati().forEach(s -> {
                                                            try {
                                                                Transport.send("Stiri Adaugate", s);
                                                            } catch (IOException e) {
                                                                e.printStackTrace();
                                                            }
                                                        });
                                                    }
                                                }
                                            }
                                            Transport.send("Stiri Adaugate", socket);
                                        }
                                    }
                                    if (message.equals("6")) {
                                        Transport.send("Nume:", socket);
                                        String name = Transport.receive(socket);
                                        Transport.send(name, socket);
                                        boolean aux = false;
                                        for (NewsChanel newsChanel : listaCanale) {
                                            if (newsChanel.getNume().equals(name))
                                                if (newsChanel.getClientiAbonati().contains(socket))
                                                    aux = true;
                                        }

                                        if (!aux) {
                                            Transport.send("NU SUNTETI ABONAT LA ACEST CANAL!", socket);
                                        } else {
                                            for (NewsChanel newsChanel : listaCanale) {
                                                if (newsChanel.getNume().equals(name))
                                                    newsChanel.removeAbonati(socket);
                                            }
                                            Transport.send("ATI PARASIT CANALUL", socket);
                                        }
                                    }
                                } catch (Exception ignored) {
                                }
                            }
                        } catch (Exception e) {
                            clients.remove(socket);
                        }
                    });
                } catch (Exception ignored) {
                }
            }
        });
    }
    public void stop() throws IOException {
        if (executorService != null) {
            executorService.shutdown();
            executorService = null;
        }
        if (serverSocket != null) {
            serverSocket.close();
            serverSocket = null;
        }

    }

}