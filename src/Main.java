import java.util.*;
import java.util.stream.Collectors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    @FunctionalInterface
    interface SafetyRule<T> {
        boolean validate(T item);
    }

    static class InvalidCapacityException extends Exception {
        InvalidCapacityException(String message) {
            super(message);
        }
    }

    static class CargoSafetyException extends RuntimeException {
        CargoSafetyException(String message) {
            super(message);
        }
    }

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

    static class GoodsBogie {
        private final String type;
        private final String cargo;

        GoodsBogie(String type, String cargo) {
            this.type = type;
            this.cargo = cargo;
        }

        public String getType() {
            return type;
        }

        public String getCargo() {
            return cargo;
        }

        @Override
        public String toString() {
            return "GoodsBogie{type='" + type + "', cargo='" + cargo + "'}";
        }
    }

    static class PassengerBogie {
        private final String name;
        private final int capacity;

        PassengerBogie(String name, int capacity) throws InvalidCapacityException {
            if (capacity <= 0) {
                throw new InvalidCapacityException("Invalid capacity for " + name + ": " + capacity);
            }
            this.name = name;
            this.capacity = capacity;
        }

        @Override
        public String toString() {
            return "PassengerBogie{name='" + name + "', capacity=" + capacity + "}";
        }
    }

    private static void assignCargo(String shape, String cargo) {
        if ("Rectangular".equalsIgnoreCase(shape) && "Petroleum".equalsIgnoreCase(cargo)) {
            throw new CargoSafetyException("Petroleum cannot be assigned to rectangular bogie.");
        }
        System.out.println("UC15 - Cargo assigned successfully: " + cargo + " -> " + shape + " bogie");
    }

    private static void bubbleSort(int[] values) {
        for (int i = 0; i < values.length - 1; i++) {
            for (int j = 0; j < values.length - i - 1; j++) {
                if (values[j] > values[j + 1]) {
                    int temp = values[j];
                    values[j] = values[j + 1];
                    values[j + 1] = temp;
                }
            }
        }
    }

    private static boolean linearSearch(String[] ids, String key) {
        for (String id : ids) {
            if (id.equals(key)) {
                return true;
            }
        }
        return false;
    }

    private static boolean binarySearch(String[] sortedIds, String key) {
        int low = 0;
        int high = sortedIds.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int comparison = key.compareTo(sortedIds[mid]);

            if (comparison == 0) {
                return true;
            } else if (comparison < 0) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return false;
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

        List<GoodsBogie> goodsBogies = List.of(
                new GoodsBogie("Cylindrical", "Petroleum"),
                new GoodsBogie("Box", "Coal"),
                new GoodsBogie("Flat", "Steel")
        );

        SafetyRule<GoodsBogie> safetyRule = bogie ->
                !"Cylindrical".equalsIgnoreCase(bogie.getType())
                        || "Petroleum".equalsIgnoreCase(bogie.getCargo());

        boolean isSafetyCompliant = goodsBogies.stream().allMatch(safetyRule::validate);

        System.out.println("\nUC12 - Safety Compliance Check:");
        goodsBogies.forEach(System.out::println);
        System.out.println("Train safety compliant? " + isSafetyCompliant);

        List<Bogie> benchmarkBogies = new ArrayList<>();
        for (int i = 0; i < 100_000; i++) {
            benchmarkBogies.add(new Bogie("P" + i, "Passenger", 20 + (i % 90)));
        }

        long loopStart = System.nanoTime();
        List<Bogie> loopFiltered = new ArrayList<>();
        for (Bogie bogie : benchmarkBogies) {
            if (bogie.getCapacity() > 60) {
                loopFiltered.add(bogie);
            }
        }
        long loopEnd = System.nanoTime();

        long streamStart = System.nanoTime();
        List<Bogie> streamFiltered = benchmarkBogies.stream()
                .filter(b -> b.getCapacity() > 60)
                .toList();
        long streamEnd = System.nanoTime();

        System.out.println("\nUC13 - Performance Comparison (Loops vs Streams):");
        System.out.println("Loop filtered count: " + loopFiltered.size() + ", time(ns): " + (loopEnd - loopStart));
        System.out.println("Stream filtered count: " + streamFiltered.size() + ", time(ns): " + (streamEnd - streamStart));

        List<PassengerBogie> validatedPassengerBogies = new ArrayList<>();
        try {
            PassengerBogie sleeperCoach = new PassengerBogie("Sleeper Coach", 72);
            validatedPassengerBogies.add(sleeperCoach);
            System.out.println("\nUC14 - Valid bogie created: " + sleeperCoach);
        } catch (InvalidCapacityException e) {
            System.out.println("\nUC14 - " + e.getMessage());
        }

        try {
            PassengerBogie invalidCoach = new PassengerBogie("Invalid Coach", 0);
            validatedPassengerBogies.add(invalidCoach);
        } catch (InvalidCapacityException e) {
            System.out.println("UC14 - Validation failed: " + e.getMessage());
        }

        System.out.println("UC14 - Passenger bogies added after validation: " + validatedPassengerBogies.size());

        try {
            System.out.println("\nUC15 - Attempting unsafe cargo assignment...");
            assignCargo("Rectangular", "Petroleum");
        } catch (CargoSafetyException e) {
            System.out.println("UC15 - Exception caught: " + e.getMessage());
        } finally {
            System.out.println("UC15 - Assignment flow completed (logged in finally).");
        }

        System.out.println("UC15 - Application continues safely after exception handling.");

        int[] capacitiesForBubbleSort = {72, 60, 24, 80, 45};
        System.out.println("\nUC16 - Capacities before Bubble Sort: " + Arrays.toString(capacitiesForBubbleSort));
        bubbleSort(capacitiesForBubbleSort);
        System.out.println("UC16 - Capacities after Bubble Sort: " + Arrays.toString(capacitiesForBubbleSort));

        String[] bogieTypeNames = {"Sleeper", "First Class", "AC Chair", "Pantry", "Cargo"};
        Arrays.sort(bogieTypeNames);
        System.out.println("\nUC17 - Sorted Bogie Names (Arrays.sort): " + Arrays.toString(bogieTypeNames));

        String[] unsortedBogieIds = {"B5", "B2", "B9", "B1", "B7"};
        String linearSearchKey = "B9";
        boolean foundByLinearSearch = linearSearch(unsortedBogieIds, linearSearchKey);
        System.out.println("\nUC18 - Linear Search on IDs " + Arrays.toString(unsortedBogieIds));
        System.out.println("UC18 - Search key " + linearSearchKey + " found? " + foundByLinearSearch);

        String[] sortedBogieIds = Arrays.copyOf(unsortedBogieIds, unsortedBogieIds.length);
        Arrays.sort(sortedBogieIds);
        String binarySearchKey = "B7";
        boolean foundByBinarySearch = binarySearch(sortedBogieIds, binarySearchKey);
        System.out.println("\nUC19 - Binary Search on sorted IDs " + Arrays.toString(sortedBogieIds));
        System.out.println("UC19 - Search key " + binarySearchKey + " found? " + foundByBinarySearch);
    }
}
