import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SirtCantasiDP {
    public static void main(String[] args) {
        String filePath = "degerler.txt";    // degerler.txt dosyasının yolunu belirtiyoruz.
        List<Item> items = new ArrayList<>();  // öğelerin değerlerini ve ağırlıklarını tutuyoruz.
        int canta_kapasite = 0; // çantanın kapasitesi için değişken tanımlıyoruz.


        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {  // dosyayı okuma işlemini yapıyoruz
            String satir; 
            int satir_No= 1; // dosyayı satır satır okuyoruz

            while ((satir = br.readLine()) != null) {
                if (satir_No == 1) { // İlk satırı değerler olarak okuyoruz
                    String[] values = satir.split(",\\s*");
                    for (String value : values) {
                        items.add(new Item(Integer.parseInt(value),0));
                    }
                } else if (satir_No == 2) { // İkinci satırı ağırlıklar olarak okuyoruz
                    String[] weights = satir.split(",\\s*");
                    for (int i = 0; i < weights.length; i++) {
                        items.get(i).setWeight(Integer.parseInt(weights[i]));
                    }
                } else if (satir_No == 3) { // Üçüncü satırı çanta kapasitesi olarak okuyoruz
                    canta_kapasite = Integer.parseInt(satir);
                }

                satir_No++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



        int[][] dp = new int[items.size() + 1][canta_kapasite + 1]; // dinamik programlama tablosu oluşturuyoruz.

        for (int i = 1; i <= items.size(); i++) {
            int value = items.get(i - 1).getValue();     //Değeri alıyoruz
            int weight = items.get(i - 1).getWeight();    //Ağırlığı alıyoruz.

            for (int j = 1; j <= canta_kapasite; j++) {
                if (weight > j) { // Ağırlık, çanta kapasitesinden büyükse önceki değeri alıyoruz
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], value + dp[i - 1][j - weight]); // Değerin maksimumunu hesaplıyoruz
                }
            }
        }


        int maxValue = dp[items.size()][canta_kapasite];  // En yüksek değeri alıyoruz
        List<Integer> used_Weights = new ArrayList<>();  // Kullanılan ağırlıkları tutmak için bir liste oluşturuyoruz
        int j = canta_kapasite;

        for (int i = items.size(); i > 0; i--) {
            if (dp[i][j] != dp[i - 1][j]) { // Kullanılan ağırlıkları belirliyoruz
                int weight = items.get(i - 1).getWeight();
                used_Weights.add(weight); // Kullanılan ağırlığı listeye ekliyoruz
                j -= weight;    // Kullanılan ağırlığı çanta kapasitesinden çıkarıyoruz
            }
        }

        System.out.println("Maximum değer: " + maxValue);
        System.out.print("Kullanilan ağirliklar: ");
        for (int i = 0; i < used_Weights.size(); i++) {
            System.out.print(used_Weights.get(i)); // kullanılan ağırlıkları ekrana yazdırıyoruz
            if (i != used_Weights.size() - 1) {
                System.out.print(", ");
            }
        }
    }
}


class Item {
    private int value; // Öğenin değeri
    private int weight; // Öğenin ağırlığı

    public Item(int value, int weight) {
        this.value = value;
        this.weight = weight;
    }

    public int getValue() {
        return value;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
