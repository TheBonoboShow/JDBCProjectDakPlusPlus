package spacedandy;

import spacedandy.model.*;
import spacedandy.services.EmployeeService;
import spacedandy.services.ProjectService;
import spacedandy.services.WorkService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        int mainChoice;
        int subChoice;

        do {
            showMainMenu();
            mainChoice = intInput(4);
            if (mainChoice != 0) {
                do {
                    showSubMenu(mainChoice);
                    if (mainChoice == 1) subChoice = intInput(6);
                    else if (mainChoice == 2) subChoice = intInput(5);
                    else if (mainChoice == 3) subChoice = intInput(4);
                    else subChoice = intInput(2);

                    handleChoice(mainChoice, subChoice);
                } while (subChoice != 0);
            }
        }
        while (mainChoice != 0);

        System.out.println("=====");
        System.out.println("Application Closed!");
    }

    private static void handleChoice(int mainChoice, int subChoice) {
        if (subChoice == 0) return;
        if (mainChoice == 1) {
            EmployeeService employeeService = new EmployeeService();
            if (subChoice == 1) {
                List<EmployeeInfo> employees = null;
                try {
                    employees = employeeService.getAllEmployees();
                    employees.forEach(e -> System.out.println(e.toString()));
                    if (employees.size() == 0) System.out.println("--You currently don't have any employees--");
                } catch (SQLException s) {
                    System.out.println("**Something went wrong, please try again**");
                }
            }
            if (subChoice == 2) {
                System.out.println("Enter the ID of the person you want to update:");
                int id = allIntInput();
                try {
                    Optional<EmployeeInfo> optionalEmployee = employeeService.getEmployeeById(id);
                    if (optionalEmployee.isPresent()) {
                        System.out.println("Do you really want to change the info for the following employee?");
                        System.out.println(optionalEmployee.get().toStringNoId());
                        int choice = yesOrNo();
                        if (choice == 1) {
                            System.out.println("Enter the correct data please:");
                            EmployeeInfo newEmployee = verifyEmployee();
                            System.out.println("Is this the correct info?");
                            System.out.println(newEmployee.toStringNoId());
                            int choice2 = yesOrNo();
                            if (choice2 == 1) {
                                employeeService.updateEmployee(id, newEmployee);
                                System.out.println("--" + newEmployee.getFirstName() + " is successfully updated--");
                            } else System.out.println("--No changes were made--");
                        } else System.out.println("--No changes were made--");
                    } else System.out.println("This is not a valid Id, please try again");
                } catch (SQLException s) {
                    System.out.println("**Something went wrong, please try again**");
                }
            }
            if (subChoice == 3) {
                System.out.println("Enter the data of the person you want to create");
                EmployeeInfo newEmployee = verifyEmployee();
                try {
                    System.out.println("Do you really want to add the following employee?");
                    System.out.println(newEmployee.toStringNoId());
                    int choice = yesOrNo();
                    if (choice == 1) {
                        employeeService.addEmployee(newEmployee);
                        System.out.println("--" + newEmployee.getFirstName() + " is successfully added--");
                    } else System.out.println("--Nobody got added--");
                } catch (SQLException s) {
                    System.out.println("**Something went wrong, please try again**");
                }
            }
            if (subChoice == 4) {
                System.out.println("Enter the ID of the person you want to delete:");
                int id = allIntInput();
                try {
                    Optional<EmployeeInfo> optionalEmployee = employeeService.getEmployeeById(id);
                    if (optionalEmployee.isPresent()) {
                        System.out.println("Do you really want to delete the following employee?");
                        System.out.println(optionalEmployee.get().toStringNoId());
                        int choice = yesOrNo();
                        if (choice == 1) {
                            employeeService.deleteEmployee(id);
                            System.out.println("--" + optionalEmployee.get().getFirstName() + " is successfully deleted--");
                        } else System.out.println("--No changes were made--");
                    } else System.out.println("This is not a valid Id, please try again");
                } catch (SQLException s) {
                    System.out.println("**Something went wrong, please try again**");
                }
            }
            if (subChoice == 5) {
                System.out.println("Which name are you looking for?");
                Scanner scanner = new Scanner(System.in);
                String extract = scanner.next();
                List<EmployeeInfo> employees = null;
                try {
                    employees = employeeService.lookForName(extract);
                    if (employees.size() == 0) System.out.println("--No matching employees found--");
                    else {
                        System.out.println("These are all the employees with " + extract + " in their name");
                        employees.forEach(e -> System.out.println(e.toString()));
                    }
                } catch (SQLException s) {
                    System.out.println("**Something went wrong, please try again**");
                }
            }
            if (subChoice == 6) {
                List<EmployeeInfo> employees = null;
                List<EmployeeInfo> employees2 = null;
                try {
                    employees = employeeService.upcomingBirthdays();
                    employees2 = employeeService.todayBirthday();
                    if (employees.size() == 0 || employees2.size() == 0)
                        System.out.println("--No upcoming birthdays--");
                    if (!employees2.isEmpty()) {
                        System.out.println("These employees are celebrating today:");
                        employees2.forEach(e -> System.out.println(e.toString()));
                    }
                    if (!employees.isEmpty()) {
                        System.out.println("Birthdays during the following 7 days:");
                        employees.forEach(e -> System.out.println(e.toString()));
                    }
                } catch (SQLException s) {
                    System.out.println("**Something went wrong, please try again**");
                }
            }
        }
        if (mainChoice == 2) {
            ProjectService projectService = new ProjectService();
            if (subChoice == 1) {
                List<Project> projects = null;
                try {
                    projects = projectService.getAllProjects();
                    projects.forEach(p -> System.out.println(p.toString()));
                    if (projects.size() == 0) System.out.println("--You currently don't have any projects--");
                } catch (SQLException s) {
                    System.out.println("**Something went wrong, please try again**");
                }
            }
            if (subChoice == 2) {
                System.out.println("Enter the data of the project you want to create");
                Project project = verifyProject();
                try {
                    System.out.println("Do you really want to add the following project?");
                    System.out.println(project.toStringNoId());
                    int choice = yesOrNo();
                    if (choice == 1) {
                        projectService.addProject(project);
                        System.out.println("--Your project is successfully added--");
                    } else System.out.println("--Nothing got added--");
                } catch (SQLException s) {
                    System.out.println("**Something went wrong, please try again**");
                }
            }
            if (subChoice == 3) {
                System.out.println("Enter the ID of the project you want to delete:");
                int id = allIntInput();
                try {
                    Optional<Project> optionalProject = projectService.getProjectById(id);
                    if (optionalProject.isPresent()) {
                        System.out.println("Do you really want to delete the following project?");
                        System.out.println(optionalProject.get().toStringNoId());
                        int choice = yesOrNo();
                        if (choice == 1) {
                            projectService.deleteProject(id);
                            System.out.println("--Your project is successfully deleted--");
                        } else System.out.println("--No changes were made--");
                    } else System.out.println("This is not a valid Id, please try again");
                } catch (SQLException s) {
                    System.out.println("**Something went wrong, please try again**");
                }
            }
            if (subChoice == 4) {
                List<Project> projects = null;
                try {
                    projects = projectService.getOngoingProjects();
                    if (projects.isEmpty())
                        System.out.println("--No ongoing projects--");
                    else {
                        System.out.println("--The following projects are live--");
                        projects.forEach(p -> System.out.println(p.toString()));
                    }
                } catch (SQLException s) {
                    System.out.println("**Something went wrong, please try again**");
                }
            }
            if (subChoice == 5) {
                List<Project> projects = null;
                try {
                    projects = projectService.showStartingToday();
                    if (projects.size() == 0) System.out.println("--No projects are starting today--");
                    else {
                        System.out.println("These projects are starting today");
                        projects.forEach(p -> System.out.println(p.toString()));
                    }
                } catch (SQLException s) {
                    System.out.println("**Something went wrong, please try again**");
                }
            }
        }
        if (mainChoice == 3) {
            WorkService workService = new WorkService();
            if (subChoice == 1) {
                List<WorkDone> workDone = null;
                try {
                    workDone = workService.getAllWorkDone();
                    workDone.forEach(p -> System.out.println(p.toString()));
                    if (workDone.size() == 0) System.out.println("--You currently don't have any registered work--");
                } catch (SQLException s) {
                    System.out.println("**Something went wrong, please try again**");
                }
            }
            if (subChoice == 2) {
                System.out.println("Enter the data of the work you want to update:");
                System.out.print("Employee Id: ");
                int id1 = allIntInput();
                System.out.print("Project Id: ");
                int id2 = allIntInput();
                System.out.print("Date (yyyy-mm-dd): ");
                String date = dateInput();
                try {
                    Optional<WorkDone> optionalWorkDone = workService.getEntry(id1, id2, date);
                    if (optionalWorkDone.isPresent()) {
                        System.out.println("Do you really want to change the info for the following employee?");
                        System.out.println(optionalWorkDone.get().toString());
                        int choice = yesOrNo();
                        if (choice == 1) {
                            System.out.println("Enter the correct data please:");
                            WorkDone workDone = verifyWorkDone();
                            System.out.println("Is this the correct info?");
                            System.out.println(workDone.toString());
                            int choice2 = yesOrNo();
                            if (choice2 == 1) {
                                workService.updateWorkDone(id1, id2, date, workDone);
                                System.out.println("--Data successfully updated--");
                            } else System.out.println("--No changes were made--");
                        } else System.out.println("--No changes were made--");
                    } else System.out.println("No data was found, please try again");
                } catch (SQLException s) {
                    System.out.println("**Something went wrong, please try again**");
                }
            }
            if (subChoice == 3) {
                System.out.println("Enter the specifics of the work that has been done");
                WorkDone workDone = verifyWorkDone();
                try {
                    System.out.println("Do you really want to add the following entry?");
                    System.out.println(workDone.toString());
                    int choice = yesOrNo();
                    if (choice == 1) {
                        workService.addWorkDone(workDone);
                        System.out.println("--Your project is successfully added--");
                    } else System.out.println("--Nothing got added--");
                } catch (SQLException s) {
                    System.out.println("**Something went wrong, please try again**");
                }
            }
            if (subChoice == 4) {
                System.out.println("Enter the data of the work you want to delete:");
                System.out.print("Employee Id: ");
                int id1 = allIntInput();
                System.out.print("Project Id: ");
                int id2 = allIntInput();
                System.out.print("Date (yyyy-mm-dd): ");
                String date = dateInput();
                try {
                    Optional<WorkDone> optionalWorkDone = workService.getEntry(id1, id2, date);
                    if (optionalWorkDone.isPresent()) {
                        System.out.println("Do you really want to delete the following data?");
                        System.out.println(optionalWorkDone.get().toString());
                        int choice = yesOrNo();
                        if (choice == 1) {
                            workService.deleteHours(id1, id2, date);
                            System.out.println("--Your worked Hours have been deleted--");
                        } else System.out.println("--No changes were made--");
                    } else System.out.println("The data was not found, please try again");
                } catch (SQLException s) {
                    System.out.println("**Something went wrong, please try again**");
                }
            }
        }
        if (mainChoice == 4) {
            ProjectService projectService = new ProjectService();
            if (subChoice == 1) {
                List<Profitability> profitability = new ArrayList<>();
                try {
                    profitability = projectService.getProfitProjects();
                    if (profitability.size() == 0) {
                        System.out.println("--No projects yet--");
                    } else {
                        System.out.println("This is every project:");
                        profitability.forEach(p -> System.out.println(p.toString()));
                    }
                } catch (SQLException s) {
                    System.out.println("**Something went wrong, please try again**");
                }
            }
            if (subChoice == 2) {
                List<HardWork> hardWorks = null;
                System.out.println("Enter the ID of the project you want to inspect:");
                int id = allIntInput();
                try {
                    Optional<Project> optionalProject = projectService.getProjectById(id);
                    if (optionalProject.isPresent()) {
                        hardWorks = projectService.getTopProductiveById(id);
                        if (hardWorks.size() == 0) System.out.println("--Nobody has worked on this project yet--");
                        else {
                            System.out.println("These are the top 3 employees with the most work done on this project:");
                            hardWorks.forEach(h -> System.out.println(h.toString()));
                        }
                    } else System.out.println("This is not a valid Id, please try again");
                } catch (SQLException s) {
                    System.out.println("**Something went wrong, please try again**");
                }
            }
        }
    }

    private static void showMainMenu() {
        System.out.println("0. Exit");
        System.out.println("1. Employee Data");
        System.out.println("2. Projects");
        System.out.println("3. Work Done");
        System.out.println("4. Check Profitability");
    }

    private static void showSubMenu(int choice) {
        if (choice == 1) {
            System.out.println("0. Back To Main Menu");
            System.out.println("1. Show All Employees");
            System.out.println("2. Update Employee Info");
            System.out.println("3. Add Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Search Records For Name");
            System.out.println("6. Check Birthdays");
        }
        if (choice == 2) {
            System.out.println("0. Back To Main Menu");
            System.out.println("1. Show All Projects");
            System.out.println("2. Add Project");
            System.out.println("3. Delete Project");
            System.out.println("4. Show Ongoing Projects");
            System.out.println("5. Show Projects Starting Today");
        }
        if (choice == 3) {
            System.out.println("0. Back To Main Menu");
            System.out.println("1. Show All Records");
            System.out.println("2. Adjust Records");
            System.out.println("3. Add Data");
            System.out.println("4. Delete Data");
        }
        if (choice == 4) {
            System.out.println("0. Back To Main Menu");
            System.out.println("1. Show All Projects");
            System.out.println("2. Show Hardest Working Employees");
        }
    }

    private static int yesOrNo() {
        System.out.println("1. Yes");
        System.out.println("2. No");
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        do {
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                choice = -1;
            }
            scanner.nextLine();
            if (choice < 1 || choice > 2) System.out.println("That is not a valid choice.");
        } while (choice < 1 || choice > 2);
        return choice;
    }

    private static int allIntInput() {
        Scanner scanner = new Scanner(System.in);
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Please put in a number: ");
            return allIntInput();
        }
    }

    private static int intInput(int numberOfChoices) {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        do {
            try {
                System.out.println("Make a choice:");
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                choice = -1;
            }
            scanner.nextLine();
            if (choice < 0 || choice > numberOfChoices) System.out.println("That is not a valid choice.");
        } while (choice < 0 || choice > numberOfChoices);
        return choice;
    }

    private static String dateInput() {
        String date = "";
        Scanner scanner = new Scanner(System.in);
        do {
            date = scanner.next();
            scanner.nextLine();
            if (!isDateValid(date))
                System.out.println("Use the right format");
        } while (!isDateValid(date));
        return date;
    }

    private static EmployeeInfo verifyEmployee() {
        EmployeeInfo employeeInfo = new EmployeeInfo();
        Scanner scanner = new Scanner(System.in);
        String regex = "[0-9]+";
        do {
            System.out.print("First Name: ");
            employeeInfo.setFirstName(scanner.next());
            scanner.nextLine();
            if (employeeInfo.getFirstName().length() < 1) System.out.println("Use at least 1 letter");
        } while (employeeInfo.getFirstName().length() < 1);
        do {
            System.out.print("Last Name: ");
            employeeInfo.setLastName(scanner.next());
            scanner.nextLine();
            if (employeeInfo.getLastName().length() < 1) System.out.println("Use at least 1 letter");
        } while (employeeInfo.getLastName().length() < 1);
        do {
            System.out.print("Telephone Number: ");
            employeeInfo.setTel(scanner.next());
            scanner.nextLine();
            if (employeeInfo.getTel().length() < 9 || employeeInfo.getTel().length() > 10)
                System.out.println("Your number is not within the possibilities (length 9 or 10)");
            if (!employeeInfo.getTel().matches(regex)) System.out.println("Please use only numbers");
            if (!employeeInfo.getTel().startsWith("0")) System.out.println("Your number does not start with 0");
        } while (employeeInfo.getTel().length() < 9 || employeeInfo.getTel().length() > 10 || !employeeInfo.getTel().startsWith("0") || !employeeInfo.getTel().matches(regex));
        do {
            System.out.print("Telephone Number Emergency Contact: ");
            employeeInfo.setTelEmergency(scanner.next());
            scanner.nextLine();
            if (employeeInfo.getTelEmergency().length() < 9 || employeeInfo.getTelEmergency().length() > 10)
                System.out.println("Your number is not within the possibilities (length 9 or 10)");
            if (!employeeInfo.getTelEmergency().matches(regex)) System.out.println("Please use only numbers");
            if (!employeeInfo.getTelEmergency().startsWith("0"))
                System.out.println("Your number does not start with 0");
            if (employeeInfo.getTel().equals(employeeInfo.getTelEmergency()))
                System.out.println("Your Emergency phone number cannot be the same as your regular number");
        } while (employeeInfo.getTelEmergency().length() < 9 || employeeInfo.getTelEmergency().length() > 10 || !employeeInfo.getTelEmergency().startsWith("0") || !employeeInfo.getTelEmergency().matches(regex) || employeeInfo.getTel().equals(employeeInfo.getTelEmergency()));
        Period period = Period.ZERO;
        do {
            System.out.print("Date Of Birth (yyyy-mm-dd): ");
            employeeInfo.setDateOfBirth(scanner.next());
            scanner.nextLine();
            if (!isDateValid(employeeInfo.getDateOfBirth()))
                System.out.println("Use the right format or check if date is possible");
            else {
                period = Period.between(LocalDate.parse(employeeInfo.getDateOfBirth()), LocalDate.now());
                if (period.getYears() < 17) System.out.println("This person is too young to work here");
            }
        } while (!isDateValid(employeeInfo.getDateOfBirth()) || period.getYears() < 17);
        do {
            System.out.print("Salary: ");
            try {
                employeeInfo.setSalary(scanner.nextDouble());
            } catch (InputMismatchException e) {
                System.out.println("Please use a number");
            }
            scanner.nextLine();
            if (employeeInfo.getSalary() <= 0) System.out.println("The salary cannot be zero or lower");
        } while (employeeInfo.getSalary() <= 0);
        return employeeInfo;
    }

    private static Project verifyProject() {
        Project project = new Project();
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print("Start Date (yyyy-mm-dd): ");
            project.setStartDate(scanner.next());
            scanner.nextLine();
            if (!isDateValid(project.getStartDate()))
                System.out.println("Use the right format and check if date is possible");
            else {
                if (ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.parse(project.getStartDate())) < 0)
                    System.out.println("A project cannot start in the past");
            }
        } while (!isDateValid(project.getStartDate()) || ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.parse(project.getStartDate())) < 0);
        do {
            System.out.print("End Date (yyyy-mm-dd): ");
            project.setEndDate(scanner.next());
            scanner.nextLine();
            if (!isDateValid(project.getEndDate()))
                System.out.println("Use the right format and check if date is possible");
            else {
                if (ChronoUnit.DAYS.between(LocalDate.parse(project.getStartDate()), LocalDate.parse(project.getEndDate())) < 0)
                    System.out.println("A project cannot end before it started");
            }
        } while (!isDateValid(project.getEndDate()) || ChronoUnit.DAYS.between(LocalDate.parse(project.getStartDate()), LocalDate.parse(project.getEndDate())) < 0);
        System.out.print("Description project: ");
        project.setDescription(scanner.nextLine());
        do {
            System.out.print("Price: ");
            try {
                project.setPrice(scanner.nextDouble());
            } catch (InputMismatchException e) {
                System.out.println("Please use a number");
            }
            scanner.nextLine();
            if (project.getPrice() <= 0) System.out.println("The Price cannot be zero or lower");
        } while (project.getPrice() <= 0);
        return project;
    }

    private static WorkDone verifyWorkDone() {
        WorkDone workDone = new WorkDone();
        Scanner scanner = new Scanner(System.in);
        ProjectService projectService = new ProjectService();
        EmployeeService employeeService = new EmployeeService();
        boolean check = false;
        do {
            System.out.print("Employee Id: ");
            try {
                workDone.setEmployeeId(scanner.nextInt());
                Optional<EmployeeInfo> optionalEmployee = employeeService.getEmployeeById(workDone.getEmployeeId());
                if (optionalEmployee.isPresent()) check = true;
            } catch (InputMismatchException | SQLException e) {
                System.out.println("Please use a number");
            }
            scanner.nextLine();
            if (!check) System.out.println("This id does not correspond to an employee");
        } while (!check);
        check = false;
        do {
            System.out.print("Project Id: ");
            try {
                workDone.setProjectId(scanner.nextInt());
                Optional<Project> optionalProject = projectService.getProjectById(workDone.getProjectId());
                if (optionalProject.isPresent()) check = true;
            } catch (InputMismatchException | SQLException e) {
                System.out.println("Please use a number");
            }
            scanner.nextLine();
            if (!check) System.out.println("This id does not correspond to a project");
        } while (!check);
        check = false;
        do {
            System.out.print("Date (yyyy-mm-dd): ");
            workDone.setDate(scanner.next());
            scanner.nextLine();
            if (!isDateValid(workDone.getDate()))
                System.out.println("Use the right format");
            else {
                int id = workDone.getProjectId();
                try {
                    check = (ChronoUnit.DAYS.between(LocalDate.parse(workDone.getDate()), LocalDate.parse(projectService.getStartDateById(workDone.getProjectId()))) <= 0);
                } catch (SQLException s) {
                    System.out.println("**Something went wrong, please try again**");
                }
                if (!check) System.out.println("You cannot add worked hours to a project before it has started");
            }
        } while (!isDateValid(workDone.getDate()) || !check);
        double temp;
        do {
            System.out.print("Hours worked: ");
            try {
                workDone.setHoursWorked(scanner.nextDouble());
            } catch (InputMismatchException e) {
                System.out.println("Please use a number");
            }
            scanner.nextLine();
            temp = workDone.getHoursWorked();
            if (temp <= 0 || temp > 24) System.out.println("The number needs to be higher than 0 and not more than 24");
        } while (temp <= 0 || temp > 24);
        System.out.print("Remarks: ");
        workDone.setRemarks(scanner.nextLine());
        return workDone;
    }

    public static boolean isDateValid(String date) {
        Pattern DATE_PATTERN = Pattern.compile("^((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$");
        return DATE_PATTERN.matcher(date).matches();
    }

}
