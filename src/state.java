/**
 * Created by light on 2017/4/17.
 */
public class state {
    int x;
    int y;
    char direction;
    state(int x, int y , char direction){
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
    String toText(){
        String result = "";
        result = "(" + this.x + "," + this.y + ")" + this.direction + "\n";
        return result;
    }
}
