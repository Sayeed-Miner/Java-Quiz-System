import java.io.*;
import java.lang.*;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JavaQuizSystem {
    public static void main(String[] args) {
        try {
            String fileLocation = "./src/main/resources/users.json";
            JSONParser parser = new JSONParser();
            JSONArray usersArray = (JSONArray) parser.parse(new FileReader(fileLocation));
            Scanner scanner = new Scanner(System.in);
            System.out.println("Output:");
            System.out.println("System:> Enter your username");
            System.out.print("User:> ");
            String username = scanner.next();
            System.out.println("System:> Enter password");
            System.out.print("User:> ");
            String password = scanner.next();
            for (int i = 0; i < usersArray.size(); i++) {
                JSONObject userObj = (JSONObject) usersArray.get(i);
                String username1 = userObj.get("username").toString();
                String password1 = userObj.get("password").toString();
                String role = userObj.get("role").toString();
                if (username.equals(username1) && password.equals(password1)) {
                    if (role.equals("admin")) {
                        System.out.println("System:> Welcome admin! Please create new questions in the question bank.");
                        createQuestion();
                        break;
                    } else if (role.equals("student")) {
                        System.out.println("System:> Welcome " + username1 + " to the quiz! We will throw you 10 questions. Each MCQ mark is 1 and no negative marking. Are you ready? Press 's' to start.");
                        System.out.println();
                        System.out.print("Student:> ");
                        char ch = scanner.next().charAt(0);
                        if (ch == 's') {
                            quiz();
                        }
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid Input");
        }
    }

    public static void createQuestion() throws IOException, ParseException {
        char ch = 's';
        String fileName = "./src/main/resources/quiz.json";
        do {
            JSONParser jsonParser = new JSONParser();
            JSONArray questionsArray = (JSONArray) jsonParser.parse(new FileReader(fileName));
            JSONObject questionObj = new JSONObject();
            Scanner input = new Scanner(System.in);
            System.out.println("System:> Input your question");
            System.out.print("Admin:> ");
            questionObj.put("question", input.nextLine());
            System.out.println("System: Input option 1: ");
            System.out.print("Admin: ");
            questionObj.put("option 1", input.nextLine());
            System.out.println("System: Input option 2: ");
            System.out.print("Admin: ");
            questionObj.put("option 2", input.nextLine());
            System.out.println("System: Input option 3: ");
            System.out.print("Admin: ");
            questionObj.put("option 3", input.nextLine());
            System.out.println("System: Input option 4: ");
            System.out.print("Admin: ");
            questionObj.put("option 4", input.nextLine());
            System.out.println("System: What is the answer key?");
            System.out.print("Admin: ");
            questionObj.put("answerkey", input.nextInt());
            questionsArray.add(questionObj);
            FileWriter file = new FileWriter(fileName);
            file.write(questionsArray.toJSONString());
            file.flush();
            file.close();
            System.out.println("System:> Saved successfully! Do you want to add more questions? (press s for start and q for quit)");
            ch = input.next().charAt(0);
        } while (ch != 'q');
    }

    public static void quiz() throws IOException, ParseException {
        Scanner scanner = new Scanner(System.in);
        char ch;
        do {
            int marks = 0;
            String fileName = "./src/main/resources/quiz.json";
            JSONParser jsonParser = new JSONParser();
            JSONArray questionsArray = (JSONArray) jsonParser.parse(new FileReader(fileName));
            for (int i = 0; i < 10; i++) {
                Random random = new Random();
                int var = random.nextInt(questionsArray.size() - 1);
                JSONObject questionObj = (JSONObject) questionsArray.get(var);
                String question = questionObj.get("question").toString();
                String option_1 = questionObj.get("option 1").toString();
                String option_2 = questionObj.get("option 2").toString();
                String option_3 = questionObj.get("option 3").toString();
                String option_4 = questionObj.get("option 4").toString();
                String answerKey = questionObj.get("answerkey").toString();
                System.out.println();
                System.out.println("[Question " + (i + 1) + "] " + question);
                System.out.println("1. " + option_1);
                System.out.println("2. " + option_2);
                System.out.println("3. " + option_3);
                System.out.println("4. " + option_4);
                System.out.println();
                System.out.print("Student:> ");
                String ans = scanner.next();
                if (answerKey.equals(ans)) {
                    marks++;
                }
            }
            if (marks >= 8) {
                System.out.println("Excellent! You have got " + marks + " out of 10");
            } else if (marks >= 5 && marks < 8) {
                System.out.println("Good. You have got " + marks + " out of 10");
            } else if (marks >= 2 && marks < 5) {
                System.out.println("Very poor! You have got " + marks + " out of 10");
            } else if (marks <= 0 || marks < 2) {
                System.out.println("Very sorry you are failed. You have got " + marks + " out of 10");
            }
            System.out.println();
            System.out.println("Would you like to start again? press s for start or q for quit");
            ch = scanner.next().charAt(0);
        } while (ch != 'q');
    }
}