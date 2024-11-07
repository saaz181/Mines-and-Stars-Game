package sample;

public class terminalPlay {
    //
//
//        System.out.println("Size of The Board: " + size);
//        System.out.println("Mines: " + gameBoard.getMines().toArray().length);
//        System.out.println("Stars: " + gameBoard.getStars().toArray().length);
//        System.out.println("Walls: " + gameBoard.getWalls().toArray().length);
//        System.out.println("");
//
//        for (int i = 0; i < gameBoard.getWalls().toArray().length; i++) {
//            String posString = "wall-" + (i+1) + ": " + gameBoard.getWalls().get(i).getPosition().toString();
//            System.out.println(posString);
//        }
//
//        System.out.println("");
//
//
//        for (int i = 0; i < gameBoard.getMines().toArray().length; i++) {
//            String minePos = "mine-" + (i+1) + ": " + gameBoard.getMines().get(i).toString();
//            System.out.println(minePos);
//        }
//
//        System.out.println("");
//
//        for (int i = 0; i < gameBoard.getStars().toArray().length; i++) {
//            String starPos = "star-" + (i+1) + ": " + gameBoard.getStars().get(i).toString();
//            System.out.println(starPos);
//        }
//
//        player1.setTurn(true);
//
//        while (!gameBoard.isGameFinished()){
//
//            if (player1.isTurn()) {
//                System.out.println("Player1: " + player1.getPlayerPosition().toString());
//
//                System.out.println("Enter position to move the " + player1.getUsername());
//                Scanner in = new Scanner(System.in);
//                int X = in.nextInt();
//                int Y = in.nextInt();
//
//                gameBoard.move(player1, new Position(X, Y));
//                System.out.println("Player1: " + player1.getPlayerPosition().toString());
//                if (this.isMoveDone(player1, new Position(X, Y))) {
//                    player1.setTurn(false);
//                    player2.setTurn(true);
//                }
//            }
//
//            else if (player2.isTurn()) {
//                System.out.println("Player2: " + player2.getPlayerPosition().toString());
//
//                System.out.println("Enter position to move the " + player2.getUsername());
//                Scanner in = new Scanner(System.in);
//                int X = in.nextInt();
//                int Y = in.nextInt();
//
//                gameBoard.move(player2, new Position(X, Y));
//                System.out.println("Player2: " + player2.getPlayerPosition().toString());
//                if (this.isMoveDone(player2, new Position(X, Y))) {
//                    player1.setTurn(true);
//                    player2.setTurn(false);
//                }
//            }
//
//        }


}
