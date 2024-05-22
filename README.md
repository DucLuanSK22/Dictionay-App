
# Dictionary Application to support learning English using Java

## Author

  1. Nguyễn Đức Luân - 20020685
  2. Lê Huy Hoàng - 20020668
  3. Nguyễn Xuân Quang - 20020463
  
## Description

The application is designed to support learning English. The application is written in Java and uses the JavaFX library. The application is based on the MVC model. The application has two types of dictionaries: English-Vietnamese and Vietnamese-English. The application use Database to store data.
  1. The application is designed to support learning English.
  2. The application is written in Java and uses the JavaFX library.
  3. The application is based on the MVC model.
  4. The application has two types of dictionaries: English-Vietnamese and Vietnamese-English.
  5. The application use Database to store data and word search features.
  6. Game-intergrated application to practice English, study time statistics and ranking user.**
  7. The application use Database to store user's data.
  8. Use Google' Cloud Text-to-Speech API to translate the entire paragraph. **
  9. Use Google' Cloud Translation API to pronounce each word.
## UML Diagram
Link
## Installation

 1. Clone the project from the repository by command: git clone -b master https://github.com/DucLuanSK22/Dictionay-App.git
  2. Open the project in the IDE.
  3. Instruction for build app:
- Project Stucture -> Modules -> Dictionary-App-main:
  + Source Folders: src\JavaCode\java
  + Resource Folders: src\resources
- Run -> Edit Configurations... -> Add new run configuration... -> Application
  + Name: app
  + MainClass: JavaCode.Dictionary_main
- Modify options --> Add VM options
- VM options: --module-path "C:\Program Files\Java\javafx-sdk-21.0.2\lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.media,javafx.web  
## Demo

### Video https://youtu.be/6aZtBpmYtUY


## Future improvements

  1. Add more dictionaries.
  2. Add more complex games.
  3. Optimize the word lookup algorithm.
  4. Integrate the application with API of Google Speech to Text to convert speech to text.
  5. Improve the user interface
##  Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.
## Project 
The project is completed.
## Notes
The application is written for educational purposes.
