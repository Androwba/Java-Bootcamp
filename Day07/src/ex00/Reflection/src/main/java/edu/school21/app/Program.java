package edu.school21.app;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        List<String> classNames = getClassNames();
        System.out.println("Classes:");
        for (String name : classNames) {
            System.out.println("  - " + name);
        }
        System.out.println("---------------------");
        System.out.print("Enter class name:\n-> ");

        Scanner scanner = new Scanner(System.in);
        String className = scanner.nextLine();

        try {
            Class<?> clazz = Class.forName("edu.school21.classes." + className);
            System.out.println("---------------------");
            System.out.println("fields:");
            for (Field field : clazz.getDeclaredFields()) {
                System.out.println("    " + field.getType().getSimpleName() + " " + field.getName());
            }
            System.out.println("methods:");
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.getDeclaringClass() == Object.class ||
                        isCommonObjectMethod(method)) {
                    continue;
                }
                System.out.print("    " + method.getReturnType().getSimpleName() + " " + method.getName() + "(");
                Class<?>[] parameterTypes = method.getParameterTypes();
                for (int i = 0; i < parameterTypes.length; i++) {
                    System.out.print(parameterTypes[i].getSimpleName());
                    if (i < parameterTypes.length - 1) {
                        System.out.print(", ");
                    }
                }
                System.out.println(")");
            }

            System.out.println("---------------------");
            System.out.println("Let’s create an object.");
            Constructor<?> constructor = clazz.getConstructor();
            Object object = constructor.newInstance();
            for (Field field : clazz.getDeclaredFields()) {
                System.out.println(field.getName() + ":");
                System.out.print("-> ");
                String value = scanner.nextLine();
                field.setAccessible(true);
                if (field.getType() == String.class) {
                    field.set(object, value);
                } else if (field.getType() == int.class) {
                    field.set(object, Integer.parseInt(value));
                }
            }
            System.out.println("Object created: " + object);
            System.out.println("---------------------");

            System.out.println("Enter name of the field for changing:");
            System.out.print("-> ");
            String fieldName = scanner.nextLine();
            System.out.println("Enter " + clazz.getDeclaredField(fieldName).getType().getSimpleName() + " value:");
            System.out.print("-> ");
            String newValue = scanner.nextLine();
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            if (field.getType() == String.class) {
                field.set(object, newValue);
            } else if (field.getType() == int.class) {
                field.set(object, Integer.parseInt(newValue));
            }
            System.out.println("Object updated: " + object);
            System.out.println("---------------------");

            System.out.println("Enter name of the method for call:");
            System.out.print("-> ");
            String methodName = scanner.nextLine();
            Method method = findMethodByName(clazz, methodName);
            Class<?>[] parameterTypes = method.getParameterTypes();
            Object[] parameterValues = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                System.out.println("Enter " + parameterTypes[i].getSimpleName() + " value:");
                System.out.print("-> ");
                String value = scanner.nextLine();
                if (parameterTypes[i] == int.class) {
                    parameterValues[i] = Integer.parseInt(value);
                } else if (parameterTypes[i] == String.class) {
                    parameterValues[i] = value;
                }
            }
            Object result = method.invoke(object, parameterValues);
            if (method.getReturnType() != void.class) {
                System.out.println("Method returned:");
                System.out.println(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static List<String> getClassNames() {
        List<String> classNames = new ArrayList<>();
        String[] possiblePaths = {
                "src/main/java/edu/school21/classes",
                "ex00/Reflection/src/main/java/edu/school21/classes"
        };
        File directory = null;
        for (String path : possiblePaths) {
            directory = new File(path);
            if (directory.exists()) {
                break;
            }
        }
        if (!directory.exists()) {
            return classNames;
        }
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isFile() && file.getName().endsWith(".java")) {
                String className = file.getName().replace(".java", "");
                classNames.add(className);
            }
        }
        return classNames;
    }

    private static boolean isCommonObjectMethod(Method method) {
        String methodName = method.getName();
        return methodName.equals("toString") ||
                methodName.equals("hashCode") ||
                methodName.equals("equals");
    }

    private static Method findMethodByName(Class<?> clazz, String methodName) throws NoSuchMethodException {
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.getName().equals(methodName)) {
                return m;
            }
        }
        throw new NoSuchMethodException("No such method found: " + methodName);
    }
}
