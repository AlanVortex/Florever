package utez.edu.mx.florever.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class ClaveGenerator {
    public static String generateCedeClave(Long idCede){
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy" ,new Locale("es-MX"));
        String date = sdf.format(new Date());
        String random = String.format("%04d", ThreadLocalRandom.current().nextInt(1,10000));
        return "C" + idCede + "-" + date + "-" + random;
    }


    public static String generateWareHouseClave(String cedeClave,Long idWarehouse){
        return null;
    }
}