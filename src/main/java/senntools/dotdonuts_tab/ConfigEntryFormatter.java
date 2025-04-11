package senntools.dotdonuts_tab;

public class ConfigEntryFormatter {
    public static Pair<Integer, Integer> getMinMax(String group){
        int min=0, max=0;
        try{
            String[] tokens = group.split(":")[0].split("-");

            if(tokens.length == 1){
                min = Integer.parseInt(tokens[0]);
                max = Integer.MAX_VALUE;
            }
            else if(tokens.length == 2){
                min = Integer.parseInt(group.split(":")[0].split("-")[0]);
                max = Integer.parseInt(group.split(":")[0].split("-")[1]);
            }
            else{
                System.err.println("Cannot process group " + group + "; invalid format detected."); // фикс от версии 1.1
            }
        }catch(NumberFormatException e){System.err.println("invalid group info, please check your config."); return null;}

        return new Pair<>(min, max);
    }
}
