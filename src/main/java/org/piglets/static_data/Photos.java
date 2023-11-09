package org.piglets.static_data;

import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.File;

public class Photos {
    public static InputFile BLACK_PIG = new InputFile(new File("src/main/resources/photo/black_pig.jpg"));
    public static InputFile WHITE_PIG = new InputFile(new File("src/main/resources/photo/white_pig.jpg"));
    public static InputFile PINK_PIG = new InputFile(new File("src/main/resources/photo/pink_pig.jpg"));
}
