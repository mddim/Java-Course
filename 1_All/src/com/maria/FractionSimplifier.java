public class FractionSimplifier {
    public static String simplify(String fraction) {
        String[] nums = fraction.split("/");
        int a = Integer.parseInt(nums[0]);
        int b = Integer.parseInt(nums[1]);
        if (b == 0 | a == 0)
            return String.valueOf(0);

        while(gcd(a, b) != 1) {
            int gcd = gcd(a, b);
            a /= gcd;
            b /= gcd;
        }
        if (b == 1)
            return String.valueOf(a);
        else
            return a + "/" + b;
    }

    public static int gcd(int a, int b) {
        while (a != b) {
            if (a > b) {
                a -= b;
            }
            else {
                b -= a;
            }
        }
        return b;
    }

    public static void main(String[] args) {
        System.out.println(simplify("4/6"));
    }
}