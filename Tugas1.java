import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.NoSuchElementException;

import aima.core.util.datastructure.XYLocation;

import wechseln.environment.WechselnBoard;
import wechseln.environment.WechselnRunner;

public class Tugas1 {
    public static void main(String[] args) throws IOException {
        ArrayList customer = new ArrayList();
        if (args.length < 3) {
            System.out.println("Usage: java Tugas1 <strategy> <input> <output>");
            System.exit(1);
        }

        String strategyName = args[0];
        String inputFilename = args[1];
        String outputFilename = args[2];

        File inputFile = new File(inputFilename);

        if(!inputFile.exists()) {
            System.err.println("Error: File " + inputFilename + " doesn't exist");
            System.exit(1);
        }

        BufferedReader in = new BufferedReader(new FileReader(inputFile.getAbsoluteFile()));
        StringTokenizer token;
        String output = "";

        try {
            token = new StringTokenizer(in.readLine());
            int n = Integer.parseInt(token.nextToken());

            WechselnBoard board = new  WechselnBoard(n);

            token = new StringTokenizer(in.readLine());
            int courier1X = Integer.parseInt(token.nextToken()) - 1;
            int courier1Y = Integer.parseInt(token.nextToken()) - 1;
            int courier2X = Integer.parseInt(token.nextToken()) - 1;
            int courier2Y = Integer.parseInt(token.nextToken()) - 1;

            board.putElement(WechselnBoard.COURIER_ONE, new XYLocation(courier1X, courier1Y));
            board.putElement(WechselnBoard.COURIER_TWO, new XYLocation(courier2X, courier2Y));

            token = new StringTokenizer(in.readLine());
            int nCustomer = Integer.parseInt(token.nextToken());
            int nRiver = Integer.parseInt(token.nextToken());
            int nOneWay = Integer.parseInt(token.nextToken());


            for(int i = 0; i < nCustomer; i++) {
                token = new StringTokenizer(in.readLine());
                int x = Integer.parseInt(token.nextToken()) - 1;
                int y = Integer.parseInt(token.nextToken()) - 1;

                board.putElement(WechselnBoard.CUSTOMER, new XYLocation(x, y));
            }

            for(int i = 0; i < nRiver; i++) {
                token = new StringTokenizer(in.readLine());
                int x = Integer.parseInt(token.nextToken()) - 1;
                int y = Integer.parseInt(token.nextToken()) - 1;

                board.putElement(WechselnBoard.RIVER, new XYLocation(x, y));
            }

            for(int i = 0; i < nOneWay; i++) {
                token = new StringTokenizer(in.readLine());
                int x1 = Integer.parseInt(token.nextToken()) - 1;
                int y1 = Integer.parseInt(token.nextToken()) - 1;
                int x2 = Integer.parseInt(token.nextToken()) - 1;
                int y2 = Integer.parseInt(token.nextToken()) - 1;

                int direction = -1;

                if(x1 == x2) {
                    // vertical
                    if(y1 > y2) {
                        // down
                        direction = WechselnBoard.ONEWAY_DOWN;
                    } else {
                        // up
                        direction = WechselnBoard.ONEWAY_UP;
                    }
                } else
                if(y1 == y2) {
                    // horizontal
                    if(x1 > x2) {
                        // left
                        direction = WechselnBoard.ONEWAY_LEFT;
                    } else {
                        // right
                        direction = WechselnBoard.ONEWAY_RIGHT;
                    }
                }

                for(int j = Math.min(x1, x2); j <= Math.max(x1, x2); j++) {
                    for(int k = Math.min(y1, y2); k <= Math.max(y1, y2); k++) {
                        board.putElement(direction, new XYLocation(j, k));
                    }
                }
            }

            WechselnRunner runner = new WechselnRunner(board);

            int strategy = strategyName.equals("ids") ? WechselnRunner.IDS :
                           strategyName.equals("astar1") ? WechselnRunner.ASTAR1 :
                           WechselnRunner.ASTAR2;

            output = runner.run(strategy);
            System.out.println(output);


        } catch (NoSuchElementException e) {
            System.err.println("Error in the input file");
            e.printStackTrace();
            System.exit(1);
        }

        in.close();

        File outputFile = new File(outputFilename);

        if(!outputFile.exists())
            outputFile.createNewFile();

        BufferedWriter out = new BufferedWriter(new FileWriter(outputFile.getAbsoluteFile()));
        out.write(output + "\n");
        out.close();
    }
}