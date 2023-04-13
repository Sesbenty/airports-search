package com.renue;

import java.util.ArrayList;
import java.util.List;

public class CSVParser {
    public CSVParser(){
    }

    public String[] Parse(String text){
        List<String> list = new ArrayList<>();
        int a = 0;
        Boolean isString = false;
        for(int i = 0; i<text.length(); i++){
            if (text.charAt(i) == ','){
                if (text.charAt(a) == '\"')
                    list.add(text.substring(a + 1, i - 1));
                else
                    list.add(text.substring(a, i));
                a = i + 1;
            }

        }

        return list.toArray(new String[0]);
    }
}
