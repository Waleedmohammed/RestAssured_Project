package basics;

import common.commonMethods;
import files.payload;
import org.testng.Assert;

public class parseComplexJson {

    public static void main(String[] args) {

        //JsonPath json = new JsonPath(payload.coursePrice());
        //int courseNumber =json.get("courses.size()");

        //Print No of courses returned by API
        int courseCount = commonMethods.elementCount(payload.coursePrice(), "courses");
        System.out.println("There are " + courseCount + " Courses");

        //Print Purchase Amount
        int purchaseAmount = commonMethods.getIntElement(payload.coursePrice(), "dashboard.purchaseAmount");
        System.out.println("Purchase Amount = " + purchaseAmount);

        //Print Title of the first course
        String firstCourseTitle = commonMethods.rowToJson(payload.coursePrice()).get("courses.title[0]");
        System.out.println("First Course Title is : " + firstCourseTitle);

        //Print All course titles and their respective Prices
        for (int i = 0; i < courseCount; i++) {
            System.out.println("Course Name is " + commonMethods.rowToJson(payload.coursePrice()).get("courses.title[" + i + "]") +
                    " and its price is " + commonMethods.rowToJson(payload.coursePrice()).get("courses.price[" + i + "]"));

            // Print no of copies sold by RPA Course
            String courseTitle = commonMethods.rowToJson(payload.coursePrice()).get("courses.title[" + i + "]");
            if (courseTitle.equalsIgnoreCase("RPA")) {
                System.out.println("RPA Course sold copies is " + commonMethods.rowToJson(payload.coursePrice()).get("courses.copies[" + i + "]") + " copies");
                break;
            }
        }


        // Verify if Sum of all Course prices matches with Purchase Amount
        int sum = 0;
        for (int i = 0; i < courseCount; i++) {

            int copies = commonMethods.rowToJson(payload.coursePrice()).getInt("courses.copies[" + i + "]");
            int pricePerCopy = commonMethods.rowToJson(payload.coursePrice()).getInt("courses.price[" + i + "]");
            int totalPrice = copies * pricePerCopy;
            sum = sum + totalPrice;

        }

        System.out.println("Total sum for all copies is : " + sum);
        Assert.assertEquals(purchaseAmount, sum);

    }
}
