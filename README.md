# Sudoku Game

### Description
The project is done using Java with Spring Boot as a backed and Angular as a frontend.  
The project has leveling system and takes sudoku grids from the files that can be found under `src/main/resources/sudoku_samples`  
Before the startup please populate the `.env` file located in the resource folder.  
![WARN](https://placehold.co/15x15/ff9966/ff9966.png) Java _version 21_ was used during the development. The project was tested using Windows machine.
---

### Startup

- Backed : `./gradew bootRun`
- Frontend : `ng serve`
- Access `http://localhost:4200`

---

### Further steps

- Fix GitHub actions builds.
- Create sudoku generator as separate service / utility.
- Split `Provider` and `Holder` services.
- Containerize project.
- Increase test and log coverage. 
- Add code style check tools.
- Add service to handle user accounts.
- Increase amount of levels and reduce hard-coded parts of number of levels.
