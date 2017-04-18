/**
 * Created by light on 2017/4/16.
 */
import java.util.*;
public class planner{
    int x;
    int y;
    char direction;
    List<Character> actions;
    List<state> result;
    static int lowerX = 1;
    static int upperX = 8;
    static int lowerY = 1;
    static int upperY = 8;
    static HashMap<String, Character> stateMap = new HashMap <String, Character> (){{
        put("LN",'W');
        put("LW",'S');
        put("LS",'E');
        put("LE",'N');

        put("RN",'E');
        put("RE",'S');
        put("RS",'W');
        put("RW",'N');
    }};

    planner(int x, int y, String direction, String action){
        this.x = x;
        this.y = y;
        this.direction = direction.charAt(0);
        this.actions = new LinkedList<>();
        for(char c : action.toCharArray()){
            if(c != ',') {
                this.actions.add(c);
            }
        }
        this.result = new LinkedList<>();
    }

    void plan(){
        //consider change direciton and position
        for(char c : actions){
            if (c == 'L' || c == 'R'){
                changeDirection(c);
                this.result.add(new state(this.x, this.y, this.direction));
            }else{
                move();
                this.result.add(new state(this.x, this.y, this.direction));
            }
        }

    }

    void move(){
        int newX = this.x,  newY = this.y;
        if(this.direction == 'E'){
            newX++;
        } else if(this.direction == 'S'){
            newY--;
        } else if(this.direction == 'W'){
            newX--;
        } else if(this.direction == 'N'){
            newY++;
        }

        //if new position is valid, update it
        if(newX >= this.lowerX && newX <= upperX &&
                newY >= this.lowerY && newY <= upperY){
            this.x = newX;
            this.y = newY;
        }
    }

    void changeDirection(char action){
        String code1 = String.valueOf(action);
        code1 += String.valueOf(this.direction);
        this.direction = this.stateMap.get(code1);
    }
}
