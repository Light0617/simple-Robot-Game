/**
 * Created by light on 2017/4/14.
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagLayout;

import java.util.List;
import javax.swing.*;
import javax.swing.Timer;
import java.lang.*;


public class world extends JFrame implements ActionListener {
    static JPanel inputStartPanel;
    static JPanel positionPanel;
    static JPanel inputActionPanel;
    static JPanel inputSendPanel;
    static JPanel inputPanel;
    static JPanel outputPanel;
    static JPanel tablePanel;
    static JLabel[][] table;
    static JButton inputButton;
    static JFrame frame;
    static JTextField inputX;
    static JTextField inputY;
    static JTextField inputDirection;
    static JTextArea inputAction;
    static JTextArea outputProcess;
    static JLabel labelStart;
    static JLabel labelX;
    static JLabel labelY;
    static JLabel labelDirection;
    static JLabel labelAction;
    static JLabel labelResult;
    static int lowerX = 1;
    static int upperX = 8;
    static int lowerY = 1;
    static int upperY = 8;
    List<state> result;

    public  void createFrame(){
        setRightArea();
        setTable();
        setFrame();
    }

    public  void setRightArea(){

        setInputPosition();
        setAction();
        setOutput();
        setInputSend();

        //put panels into inputPanel
        this.inputPanel = new JPanel();
        this.inputPanel.setLayout(new BoxLayout(this.inputPanel, BoxLayout.Y_AXIS));
        this.inputPanel.add(this.inputStartPanel);
        this.inputPanel.add(this.inputActionPanel);
        this.inputPanel.add(this.outputPanel);
        this.inputPanel.add(this.inputSendPanel);
    }

    public  void setTable(){
        //left output
        this.tablePanel = new JPanel();
        this.table = new JLabel[8][8];
        this.tablePanel.setSize(400, 400);
        this.tablePanel.setLayout(new GridLayout(8, 8));

        for (int i = 0; i < this.table.length; i++) {
            for (int j = 0; j < this.table[i].length; j++) {
                this.table[i][j] = new JLabel();
                this.table[i][j].setBackground(Color.white);
                this.table[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                this.table[i][j].setOpaque(true);
                this.tablePanel.add(this.table[i][j]);
            }
        }
    }

    public  void setInputPosition(){
        //right input start position
        this.positionPanel = new JPanel();
        this.positionPanel.setLayout(new GridLayout(3, 2));
        this.positionPanel.setSize(200, 1);

        this.labelX = new JLabel("X:");
        this.inputX = new JTextField(1);
        this.labelY = new JLabel("Y:");
        this.inputY = new JTextField(1);
        this.labelDirection =new JLabel("Direction:");
        this.inputDirection = new JTextField(1);

        this.positionPanel.add(this.labelX);
        this.positionPanel.add(this.inputX);
        this.positionPanel.add(this.labelY);
        this.positionPanel.add(this.inputY);
        this.positionPanel.add(this.labelDirection);
        this.positionPanel.add(this.inputDirection);

        this.labelStart = new JLabel("start position:");

        this.inputStartPanel = new JPanel();
        this.inputStartPanel.setSize(200, 1);
        this.inputStartPanel.setLayout(new BoxLayout(this.inputStartPanel, BoxLayout.Y_AXIS));
        this.inputStartPanel.add(this.labelStart);
        this.inputStartPanel.add(this.positionPanel);
    }

    public  void setAction(){
        //right input Action
        this.inputActionPanel = new JPanel();
        this.inputActionPanel.setLayout(new GridLayout(2, 1));
        this.inputActionPanel.setSize(200, 2);

        this.labelAction = new JLabel("Motion:");
        this.inputAction = new JTextArea(1, 5);

        JScrollPane scroller = new JScrollPane(this.inputAction);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        this.inputActionPanel.add(this.labelAction);
        this.inputActionPanel.add(scroller);
    }

    public  void setOutput(){
        this.outputPanel = new JPanel();
        this.outputPanel.setSize(200, 10);
        this.outputPanel.setLayout(new BoxLayout(this.outputPanel, BoxLayout.Y_AXIS));

        this.outputProcess = new JTextArea(10, 10);
        this.outputProcess.setLineWrap(true);
        this.outputProcess.setEditable(false);
        this.labelResult = new JLabel("result:");
        this.outputPanel.add(this.labelResult);

        JScrollPane scroller1 = new JScrollPane(outputProcess);
        scroller1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        this.outputPanel.add(this.labelResult);
        this.outputPanel.add(scroller1);
    }

    public  void setInputSend(){
        //right input send
        this.inputButton = new JButton("Send");
        this.inputButton.addActionListener(this);

        this.inputSendPanel = new JPanel();
        this.inputSendPanel.setSize(200, 1);
        this.inputSendPanel.setLayout(new BoxLayout(this.inputSendPanel, BoxLayout.Y_AXIS));

        this.inputSendPanel.add(inputButton);
    }

    public  void setFrame(){
        this.frame = new JFrame();
        this.frame.getContentPane().add(BorderLayout.CENTER, this.tablePanel);
        this.frame.getContentPane().add(BorderLayout.EAST, this.inputPanel);
        this.frame.setVisible(true);
        this.frame.setResizable(false);
        this.frame.setLocation(300, 200);
        this.frame.setSize(600, 400);
        this.frame.setTitle("new world");
    }

    public void actionPerformed(ActionEvent e){

        String StringX = this.inputX.getText();
        String StringY = this.inputY.getText();
        String direction = this.inputDirection.getText();
        String action = this.inputAction.getText();

        String errorMessage = parse(StringX, StringY, direction, action);
        if(errorMessage.length() > 1) {
            JOptionPane.showMessageDialog(null, errorMessage);
        }else {
            int x = Integer.valueOf(StringX);
            int y = Integer.valueOf(StringY);
            planner planer1 = new planner(x, y, direction, action);
            planer1.plan();
            this.result = planer1.result;
            clearTable();

            this.outputProcess.setText("");
            final state prevState = new state(-1, -1, 'E');
            for (int i = 0; i < result.size(); i++) {
                state state = result.get(i);
                int delay = 1000; //milliseconds
                ActionListener taskPerformer = new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        outputProcess.append(state.toText());
                    }
                };
                Timer t = new Timer(delay, taskPerformer);
                t.setRepeats(false);
                t.start();

                move(result, i);
                this.tablePanel.repaint();
                this.frame.repaint();
            }
        }
    }

    void move(List<state> result , int cur){
        if (cur > 0){
            clearCar(result.get(cur-1));
        }
        drawCar(result.get(cur));
    }
    void clearTable(){
        for (int i = 0; i < this.table.length; i++) {
            for (int j = 0; j < this.table[i].length; j++) {
                this.table[i][j].setIcon(null);
            }
        }
    }

    int[] transformPosition(int x, int y){
        return new int [] {upperX - y  , x - 1};
    }

    void clearCar(state prevState){
        if(prevState.x > 0){
            int [] newPosition = transformPosition(prevState.x, prevState.y);
            int x = newPosition[0];
            int y = newPosition[1];
            int direction = prevState.direction;

            //draw image
            this.table[x][y].setIcon(null);
        }
    }

    void drawCar(state state){
        //clear prev car
        int [] newPosition = transformPosition(state.x, state.y);
        int x = newPosition[0];
        int y = newPosition[1];

        char direction = state.direction;

        //insert image
        ImageIcon img = insertImage(direction);

        //draw image
        drawTable(x, y, img);
    }

    ImageIcon insertImage(char direction){
        //insert image
        ImageIcon img = new ImageIcon("/Users/light/Documents/8A/game/src/car" +
                            String.valueOf(direction) + ".jpg");
        Image image = img.getImage(); // transform it
        Image newimg = image.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way

        return new ImageIcon(newimg);
    }

    void drawTable(int x, int y, ImageIcon img){

        this.table[x][y].setIcon(img);
    }

    String parse(String x, String y, String direction, String action){
        //x
        StringBuilder errorList =  new StringBuilder();
        errorList.append(verifyX(x));

        //y
        errorList.append(verifyY(y));

        //direction
        errorList.append(verifyDirection(direction));

        //actions
        String [] s1 = action.split(",");
        for(String s : s1){
            if(s.length() > 1) {
                errorList.append("action (" + action + ") is invalid\n");
            }
        }
        for(String s : s1){
            char c = s.charAt(0);
            if(c != ',') {
                errorList.append(verifyAction(c));
            }

        }

        //if any error message
        String errorMessage = errorList.toString();
        return errorMessage;
    }

    String verifyAction(char c){

        if(c != 'M' && c != 'L' && c != 'R'){
            return "action(" + String.valueOf(c) + ") is invalid\n";
        }else{
            return "";
        }
    }

    String verifyDirection(String s){
        if (s.length() == 0){
            return "Direction is empty\n";
        }
        if (s.length() > 1){
            return "direction(" + s+  ") is too long\n";
        }
        char c = s.charAt(0);
        if(c != 'N' && c != 'E' && c != 'S' && c != 'W'){
            return "direction(" + String.valueOf(c) +
                    ") is invalid\n";
        }
        return "";
    }

    String verifyX(String s){
        if(s.length() > 1){
            return "x(" + s+ ") is out of bound!\n";
        }
        if (s.length() == 0){
            return "x is empty\n";
        }
        char c = s.charAt(0);
        if(!Character.isDigit(c)){
            return "x(" + c + ") is not digit!\n";
        }
        int x = c - '0';
        if(x < lowerX || x >= upperX){
            return "x(" + String.valueOf(x) + ") is out of bound!\n";
        }else{
            return "";
        }
    }

    String verifyY(String s){
        if(s.length() > 1){
            return "y(" + s+ ") is out of bound!\n";
        }
        if (s.length() == 0){
            return "y is empty\n";
        }
        char c = s.charAt(0);
        if(!Character.isDigit(c)){
            return "y(" + c + ") is not digit!\n";
        }
        int y = c - '0';
        if(y < lowerY || y >= upperY){
            return "y(" + String.valueOf(y) + ") is out of bound!\n";
        }else{
            return "";
        }
    }

    public static void main(String[] args) {
        world w = new world();
        w.createFrame();
    }
}
