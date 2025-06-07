# Carbon Footprint Calculator (Philippines)

A desktop application built with Java and JavaFX to calculate a user's annual carbon footprint and provide personalized recommendations for reduction, with a specific focus on the Philippine context.

## Features

* **Philippines-Specific Data:** Prioritizes the use of emission factors researched specifically for the Philippine context where available, ensuring more accurate and relevant calculations.
* **Comprehensive Data Input:** Collects user data across six key lifestyle categories: Housing & Energy, Diet, Transportation, Waste, and Goods.
* **Visual Breakdown:** Displays a per-category breakdown of the user's footprint in a bar chart on the results page, complete with data labels for clarity.
* **Personalized Recommendations:** Generates actionable advice tailored to the user's specific inputs to help them reduce their environmental impact.

## Technology Stack

* **Language:** Java 17+
* **Framework:** JavaFX 17+
* **Architecture:** Model-View-Controller (MVC)

## Setup and Running the Project

To run this project, you will need a JDK (version 17 or higher) and the JavaFX SDK.

### Prerequisites

1.  **JDK 17+:** Make sure you have a Java Development Kit installed.
2.  **JavaFX SDK:** Download the JavaFX SDK appropriate for your operating system and JDK version from [GluonHQ](https://gluonhq.com/products/javafx/). Unzip it to a memorable location.
3.  **IDE:** An IDE like IntelliJ IDEA (Community or Ultimate) is recommended.

### Running from IntelliJ IDEA

1.  **Clone the Repository:**
    ```bash
    git clone https://github.com/erudyey02/Carbon-Footprint-Calculator.git
    ```
2.  **Open the Project:** Open the cloned folder in IntelliJ IDEA.

3.  **Configure Project SDK:**
    * Go to `File -> Project Structure -> Project`.
    * Select your installed JDK 17+ as the **Project SDK**.

4.  **Add JavaFX as a Project Library (Crucial Step):**
    * In the same `Project Structure` window, go to the **Libraries** tab.
    * Click the **`+`** button (Add) and select **Java**.
    * Navigate to the location where you unzipped the JavaFX SDK and select the `lib` folder.
    * Click **OK** to add it as a project library.

5.  **Configure Run Options:**
    * Go to `Run -> Edit Configurations...`.
    * Find the `CarbonFootprintAppMain` configuration.
    * Because this project is **modular** (contains `module-info.java`), the only VM option that may be required is:

    **(Not Required) VM Options:**
    ```
    --enable-native-access=javafx.graphics
    ```

6.  **Run the Application:** Click the "Run" button.
