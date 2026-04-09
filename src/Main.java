import java.util.*;
import java.util.stream.Collectors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static class Bogie {
        private final String name;
        private final String type;
        private final int capacity;

        Bogie(String name, String type, int capacity) {
            this.name = name;
            this.type = type;
            this.capacity = capacity;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public int getCapacity() {
            return capacity;
        }

        @Override
        public String toString() {
            return name + " (" + type + ") -> Capacity: " + capacity;
        }
    }

    public static void main(String[] args) {

        System.out.println("=== Train Consist Management App ===");

        List<String> consist = new ArrayList<>();

        System.out.println("Train initialized.");
        System.out.println("Initial bogie count: " + consist.size());

        List<String> passengerBogies = new ArrayList<>();

        passengerBogies.add("Sleeper");
        passengerBogies.add("AC Chair");
        passengerBogies.add("First Class");

        System.out.println("\nAfter adding bogies: " + passengerBogies);

        passengerBogies.remove("AC Chair");

        System.out.println("After removing AC Chair: " + passengerBogies);

        System.out.println("Is Sleeper present? " + passengerBogies.contains("Sleeper"));

        Set<String> bogieIds = new HashSet<>();

        bogieIds.add("B1");
        bogieIds.add("B2");
        bogieIds.add("B3");
        bogieIds.add("B2");

        System.out.println("\nUnique Bogie IDs: " + bogieIds);

        LinkedList<String> train = new LinkedList<>();

        train.add("Engine");
        train.add("Sleeper");
        train.add("AC");
        train.add("Cargo");
        train.add("Guard");

        train.add(2, "Pantry");

        System.out.println("\nTrain after adding Pantry at position 2: " + train);

        train.removeFirst();
        train.removeLast();

        System.out.println("Final Train Consist: " + train);

        LinkedHashSet<String> formation = new LinkedHashSet<>();

        formation.add("Engine");
        formation.add("Sleeper");
        formation.add("Cargo");
        formation.add("Guard");
        formation.add("Sleeper");

        System.out.println("\nOrdered Unique Formation: " + formation);

        HashMap<String, Integer> capacityMap = new HashMap<>();

        capacityMap.put("Sleeper", 72);
        capacityMap.put("AC Chair", 60);
        capacityMap.put("First Class", 24);

        System.out.println("\nBogie Capacity Details:");
        for (Map.Entry<String, Integer> entry : capacityMap.entrySet()) {
            System.out.println(entry.getKey() + " -> Capacity: " + entry.getValue());
        }

        List<Bogie> passengerBogieObjects = new ArrayList<>();
        passengerBogieObjects.add(new Bogie("Sleeper", "Passenger", 72));
        passengerBogieObjects.add(new Bogie("AC Chair", "Passenger", 60));
        passengerBogieObjects.add(new Bogie("First Class", "Passenger", 24));

        passengerBogieObjects.sort(Comparator.comparingInt(Bogie::getCapacity));

        System.out.println("\nUC7 - Sorted Passenger Bogies by Capacity:");
        passengerBogieObjects.forEach(System.out::println);

        List<Bogie> highCapacityBogies = passengerBogieObjects.stream()
                .filter(b -> b.getCapacity() > 60)
                .toList();

        System.out.println("\nUC8 - Filtered Passenger Bogies (capacity > 60):");
        highCapacityBogies.forEach(System.out::println);

        List<Bogie> allBogies = new ArrayList<>(passengerBogieObjects);
        allBogies.add(new Bogie("Tank Wagon", "Goods", 0));
        allBogies.add(new Bogie("Box Wagon", "Goods", 0));

        Map<String, List<Bogie>> bogiesByType = allBogies.stream()
                .collect(Collectors.groupingBy(Bogie::getType));

        System.out.println("\nUC9 - Grouped Bogies by Type:");
        bogiesByType.forEach((type, bogies) -> {
            System.out.println(type + ":");
            bogies.forEach(bogie -> System.out.println("  " + bogie));
        });

        int totalSeats = passengerBogieObjects.stream()
                .map(Bogie::getCapacity)
                .reduce(0, Integer::sum);

        System.out.println("\nUC10 - Total Seating Capacity: " + totalSeats);

        String trainIdInput = "TRN-1234";
        String cargoCodeInput = "PET-AB";

        Pattern trainIdPattern = Pattern.compile("TRN-\\d{4}");
        Pattern cargoCodePattern = Pattern.compile("PET-[A-Z]{2}");

        Matcher trainIdMatcher = trainIdPattern.matcher(trainIdInput);
        Matcher cargoCodeMatcher = cargoCodePattern.matcher(cargoCodeInput);

        boolean isTrainIdValid = trainIdMatcher.matches();
        boolean isCargoCodeValid = cargoCodeMatcher.matches();

        System.out.println("\nUC11 - Regex Validation:");
        System.out.println("Train ID (" + trainIdInput + ") valid? " + isTrainIdValid);
        System.out.println("Cargo Code (" + cargoCodeInput + ") valid? " + isCargoCodeValid);
    }
}
