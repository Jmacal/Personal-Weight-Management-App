import java.lang.Math;

public class Weight {

    private final int POUNDS_IN_STONE = 14;
    private final float KILOGRAMS_IN_POUND = 0.453592f;

    private float weightKilograms;

    public Weight(float kilograms) {
        weightKilograms = kilograms;
    }

    public Weight(int stone, int pounds) {
        int totalPounds = (stone * POUNDS_IN_STONE) + pounds;
        weightKilograms = totalPounds * KILOGRAMS_IN_POUND;
    }

    public float getKilograms() {
        return weightKilograms;
    }

    public int[] getStonePounds() {
        int[] stonePounds = new int[2];
        int totalPounds = Math.round(weightKilograms / KILOGRAMS_IN_POUND);
        stonePounds[0] = totalPounds / POUNDS_IN_STONE;
        stonePounds[1] = totalPounds % POUNDS_IN_STONE;
        return stonePounds;
    }

    public static void main(String[] args) {
        Weight testWeight = new Weight(40.8f);
        System.out.printf("%3f kilograms\n", testWeight.getKilograms());
        int[] stonePounds = testWeight.getStonePounds();
        System.out.printf("%3d stone, %3d pounds", stonePounds[0], stonePounds[1]);
    }
}