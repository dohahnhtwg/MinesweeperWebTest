package minesweeper.util;

import minesweeper.model.IField;
import play.libs.Json;

import java.util.HashMap;
import java.util.Map;

public class JsonConverter {

    public static String createJsonFromMultiArray(IField field) {
        int x = field.getLines();
        int y = field.getColumns();
        Map<String, Object> jsonField[][] = new HashMap[x][y];
        for (int i=0; i < x; i++) {
            for (int j=0; j < y; j++) {
                jsonField[i][j] = new HashMap<String, Object>();
                jsonField[i][j].put("value", field.getPlayingField()[i + 1][j + 1].getValue());
                jsonField[i][j].put("isRevealed", field.getPlayingField()[i + 1][j + 1].isRevealed());
                jsonField[i][j].put("row", i + 1);
                jsonField[i][j].put("column", j + 1);

            }
        }
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("victory", field.isVictory());
        json.put("loose", field.isGameOver());
        json.put("field", jsonField);
        json.put("type", "UpdateMessage");
        return Json.stringify(Json.toJson(json));
    }
}
