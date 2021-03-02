import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Clasa Main contine functia main a programului.
 */
public class Main {
    /**
     * @param args [tip_interfata] [delay_instructiuni] [nume_fisier_input]
     * Functia parseaza comenzile de input din fisierul de input si apeleaza
     * functiile corespunzatoare din clasa Facade.
     */
    public static void main(String[] args) {
        Facade facade = new Facade();
        String inputFileName = "input.txt";

        if(args[0].equals("cli"))
        {
            Constants.MOD = 0;
            Constants.DELAY = Integer.parseInt(args[1]);
            inputFileName = args[2];
        }

        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(inputFileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String[] currentArgs;

        while(scanner != null && scanner.hasNext())
        {
            currentArgs = scanner.nextLine().split(";");

            try {
                switch (currentArgs[0]) {
                    case "newClient" -> facade.NewClient(currentArgs);
                    case "newProdus" -> facade.NewProdus(currentArgs);
                    case "newBroker" -> facade.newBroker();
                    case "solicitareLicitatie" -> facade.SolicitareLicitatie(currentArgs);
                    case "afisareProduse" -> facade.AfisareProduseClient(currentArgs);
                    case "quit" -> {
                        System.out.println("Quiting!");
                        System.exit(0);
                    }
                }
            } catch (Exception | ProdusInvalidException e)
            {
                e.printStackTrace();
            }
            try {
                Thread.sleep(Constants.DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
