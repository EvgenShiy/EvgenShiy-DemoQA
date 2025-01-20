package utils;

import com.github.javafaker.Faker;

import java.util.*;
import java.util.stream.Collectors;

public class RandomUtils {

    private final Faker faker = new Faker();

    public final String getRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = getRandomInt(0, characters.length() - 1);
            randomString.append(characters.charAt(index));
        }

        return randomString.toString();
    }

    public final String generateStrongPassword(int length) {
        if (length < 8) {
            throw new IllegalArgumentException("Пароль должен быть хотя бы 8 символов.");
        }

        String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCase = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String specialChars = "!@#$%^&*()-_=+";

        Random random = new Random();

        char upper = upperCase.charAt(random.nextInt(upperCase.length()));
        char lower = lowerCase.charAt(random.nextInt(lowerCase.length()));
        char digit = digits.charAt(random.nextInt(digits.length()));
        char special = specialChars.charAt(random.nextInt(specialChars.length()));

        // Создаем StringBuilder для пароля
        StringBuilder password = new StringBuilder();

        // Добавляем обязательные символы
        password.append(upper);
        password.append(lower);
        password.append(digit);
        password.append(special);

        // Дополняем пароль случайными символами до нужной длины
        String allChars = upperCase + lowerCase + digits + specialChars;
        for (int i = password.length(); i < length; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        // Перемешиваем символы, чтобы не было предсказуемого порядка
        String passwordStr = password.toString();
        char[] passwordArray = passwordStr.toCharArray();
        for (int i = 0; i < passwordArray.length; i++) {
            int j = random.nextInt(passwordArray.length);
            char temp = passwordArray[i];
            passwordArray[i] = passwordArray[j];
            passwordArray[j] = temp;
        }

        return new String(passwordArray);
    }

    public final int getRandomInt(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    public final String getRandomFirstName() {
        return faker.name().firstName();
    }

    public final String getRandomLastName() {
        return faker.name().lastName();
    }

    public final String getRandomGender() {
        String[] genders = {"Male", "Female", "Other"};
        return getRandomItemFromArray(genders);
    }

    public final String getRandomItemFromArray(String[] array) {
        int index = getRandomInt(0, array.length - 1);
        return array[index];
    }

    public final String getRandomPhone() {
        return faker.phoneNumber().subscriberNumber(10);
    }

    public final String getRandomEmail() {
        return getRandomString(10) + "@gmail.com";
    }

    public final String getRandomAddress() {
        return faker.address().fullAddress();
    }

    public final String getRandomDayOfBirth() {
        return String.format("%02d", faker.number().numberBetween(1, 28));
    }

    public final String getRandomMonthOfBirth() {
        return faker.options().option("January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December");
    }

    public final String getRandomYearOfBirth() {
        return String.valueOf(faker.number().numberBetween(1900, 2024));
    }

    public final String getRandomSubject() {
        String[] subjects = {"Maths", "Physics", "English", "Chemistry"};
        return getRandomItemFromArray(subjects);
    }

    public final String getRandomHobby() {
        String[] hobbies = {"Sports", "Reading", "Music"};
        return getRandomItemFromArray(hobbies);
    }

    public final String getRandomFoto() {
        String[] fotos = {"testfile.png", "testfile2.png", "testfile3.png"};
        return getRandomItemFromArray(fotos);
    }

    public final String getRandomState() {
        String[] states = {"NCR", "Uttar Pradesh", "Haryana", "Rajasthan"};
        return getRandomItemFromArray(states);
    }

    public final String getRandomCity(String state) {
        Map<String, String[]> stateCitiesMap = new HashMap<>();
        stateCitiesMap.put("NCR", new String[]{"Delhi", "Gurgaon", "Noida"});
        stateCitiesMap.put("Uttar Pradesh", new String[]{"Agra", "Lucknow", "Merrut"});
        stateCitiesMap.put("Haryana", new String[]{"Karnal", "Panipat"});
        stateCitiesMap.put("Rajasthan", new String[]{"Jaipur", "Jaiselmer"});

        String[] cities = stateCitiesMap.get(state);
        return getRandomItemFromArray(cities);
    }
}