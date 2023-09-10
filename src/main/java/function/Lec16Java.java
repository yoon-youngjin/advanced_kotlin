package function;

public class Lec16Java {
    public static void main(String[] args) {
        StringFilter filter = new StringFilter() {
            @Override
            public boolean predicate(String str) {
                return str.startsWith("A");
            }
        };

        StringFilter filter2 = s -> s.startsWith("A");

    }
}
