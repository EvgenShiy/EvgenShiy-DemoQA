package utils;

public class StudentData {
    private static final RandomUtils randomUtils = new RandomUtils();

    public String firstName = randomUtils.getRandomFirstName();
    public String lastName = randomUtils.getRandomLastName();
    public String gender = randomUtils.getRandomGender();
    public String phone = randomUtils.getRandomPhone();
    public String email = randomUtils.getRandomEmail();
    public String address = randomUtils.getRandomAddress();
    public String dayOfBirth = randomUtils.getRandomDayOfBirth();
    public String monthOfBirth = randomUtils.getRandomMonthOfBirth();
    public String yearOfBirth = randomUtils.getRandomYearOfBirth();
    public String subject = randomUtils.getRandomSubject();
    public String hobby = randomUtils.getRandomHobby();
    public String photo = randomUtils.getRandomPhoto();
    public String state = randomUtils.getRandomState();
    public String city = randomUtils.getRandomCity(state);

    public static StudentData generate() {
        return new StudentData();
    }
}