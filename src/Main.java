import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Arrays;

public class Main {
    //Определение цветов для изменения текста
    //Начало
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";
    //Конец
    public static void main(String[] args) throws IOException {
        //заполнение массива слов из файла wordlist.txt
        //Начало
        BufferedReader reader = new BufferedReader(new FileReader("src/wordlist.txt"));
        String str;
        ArrayList<String> list = new ArrayList<String>();
        while ((str = reader.readLine()) != null) {
            if (!str.isEmpty()) {
                list.add(str.toLowerCase(Locale.ROOT));
            }
        }
        String[] stringArr = list.toArray(new String[0]);
        //Конец

        int sum1 = 0; //переменная, считающая количество несовпадений
        int errs = 0; //переменная, считающая количество неверных ответов
        int randomIndex = (int) Math.floor(Math.random() * stringArr.length); // рандомная переменная в диапазоне от 0 до количества слов из списка wordlist.txt
        String word = stringArr[randomIndex]; //переменная с загаданным словом
        String[] quest = word.split(""); //Массив, состоящий из букв загаданного слова
        String[] answer = new String[word.length()]; //Массив длиной с загаданное слово, который изначально заполняется симолами "_", а впоследствии заполняется угаданными буквами
        String entered = ""; //Строка, заполняемая ввдёнными и проверенными буквами
        boolean asWin = false; //флаг определения победы

        //Заполение массива answer символами "_"
        Arrays.fill(answer, "_");

        // Приветствие и приглашение к игре
        //Начало
        System.out.println("Привет! Предлагаю сыграть в игру Виселица." +
                "\nЯ уже загадал английское слово, а твоя задача отгадать его\n" + String.join("", answer) +
                "\n Но помни, у тебя есть право всего на 7 ошибок." +
                "\nПриступим. Вводи букву:");
        //Конец
        System.out.println(word);
        Scanner scanner = new Scanner(System.in);
        //Цикл, определяющий правила игры
        //Начало
        while (errs < 7 && !asWin) {
            //Ввод и проверка введённого символа
            //Начало
            String letter = scanner.nextLine().toLowerCase();
            Pattern p = Pattern.compile("[a-z]");
            Matcher m = p.matcher(letter);
            //Конец

            if (m.matches() && letter.length() == 1) {
                if (!entered.contains(letter)) { //Проверка на уже введённые буквы
                    for (int i = 0; i < answer.length; i++) {
                        if (letter.equals(quest[i])) {
                            answer[i] = letter;
                        } else {
                            sum1 += 1;
                        }

                        if (sum1 == answer.length) {
                            errs += 1;
                        }
                        System.out.print(ANSI_YELLOW + answer[i] + ANSI_RESET);
                        if (Arrays.equals(answer, quest)) {
                            asWin = true;
                        }
                    }

                    if (!asWin) {
                        System.out.println("\nКоличество оставшихся ошибок: " + (7 - errs));
                        System.out.println("Вводи следующую букву:");
                    }

                    sum1 = 0;
                } else System.out.println("Ты уже вводил эту букву, попробуй ещё: ");
            } else System.out.println("Введите одну букву английского алфавита: ");
            entered = entered.concat(letter);
         }
        //Конец

        if (asWin) {
            System.out.print(ANSI_GREEN + "\n\nМолоцец! Ты справился! \n\nПоздравляю!");
            System.exit(0);
        } else {
            System.out.println("Прости, видимо не твой день! В следующий раз повезёт;)\nПравильное слово: " + ANSI_RED + word + ANSI_RESET);
        }
    }
}