package cn.ussshenzhou.rainbow6.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * @author USS_Shenzhou
 */
public class R6ServerMapConfigManager {
    private static MinecraftServer server = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
    private static final Path FOLDER = server.getDataDirectory().toPath().resolve("config").resolve("rainbow6").resolve("maps");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public static ArrayList<String> getMapNames(){
        ArrayList<String> fileNames = new ArrayList<>();
        if(!FOLDER.toFile().isDirectory()){
            try{
                Files.createDirectories(FOLDER);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        File[] files = FOLDER.toFile().listFiles();
        for (File f : files){
            fileNames.add(f.getName().replace(".json",""));
        }
        return fileNames;
    }

    public static R6ServerMapConfig getR6Config(String name){
        R6ServerMapConfig config = null;

        if(!FOLDER.toFile().isDirectory()){
            try{
                Files.createDirectories(FOLDER);
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        Path configPath = FOLDER.resolve(name+".json");
        if (configPath.toFile().isFile()){
            try {
                config = GSON.fromJson(FileUtils.readFileToString(configPath.toFile(), StandardCharsets.UTF_8),R6ServerMapConfig.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                config = new R6ServerMapConfig(name);
                FileUtils.write(configPath.toFile(),GSON.toJson(config),StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return config;
    }

    public static void saveR6Config(R6ServerMapConfig config){
        if (!FOLDER.toFile().isDirectory()){
            try {
                Files.createDirectories(FOLDER);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Path configPath = FOLDER.resolve(config.getMapName()+".json");
        try {
            FileUtils.write(configPath.toFile(),GSON.toJson(config),StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
