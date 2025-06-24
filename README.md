# QuizApp

QuizApp is a team-based football trivia game built for Android devices. Two teams compete to answer questions from four categories: National Teams, Clubs, Geography and General football knowledge. The app currently supports local multiplayer on a single device.

## Gameplay Overview
* Two teams enter their names and optionally choose team images.
* Teams take turns selecting a category. Each question is timed.
* Correct answers earn a point. The first team to reach the configured score wins.
* Scores per category are tracked so you can see each team's strengths.
* At the end of the match a summary screen displays the winner and full statistics.

The question database resides in Firebase Realtime Database. Each question has a `displayed` flag which is set to `true` once used so that questions are not repeated in a single game. A helper class resets these flags at game start.

## Architecture
The project is written in Java using Android Studio and Gradle. Important components include:

* **MainActivity** – entry screen where team names, language and images are chosen.
* **SelectCategory** – lets the playing team pick a quiz category and view current scores.
* **MainGame** – displays questions, handles the countdown timer and determines whether answers are correct.
* **GameOver** – shows the final result and offers rematch or share options.
* **FirebaseDBHelper** – communicates with Firebase Realtime Database to load questions and mark them as shown.
* **GameState** – a serializable object used by `DataBetweenActivitiesManager` to pass the current game state between activities.
* **FMS** – Firebase Messaging Service used for push notifications.

The assets folder contains text files with sample questions that can be uploaded to Firebase using the `scripts/firebase_migrate.py` helper.

## Future Roadmap
The application started as an offline trivia experience but it is designed to be expanded. Upcoming milestones include:

1. **Online Multiplayer** – allow teams on different devices to play against each other over the internet. Firebase Realtime Database or Firestore can keep game state in sync.
2. **Authentication** – integrate Firebase Authentication with Google Sign-In so players can create profiles and preserve their stats across devices.
3. **Improved Messaging** – replace the current basic FCM implementation with a more reliable real-time communication channel for turn notifications and lobby chat.
4. **Topic Expansion** – add more categories and support downloadable question packs so quizzes stay fresh.
5. **Leverage Free Firebase Features** – such as Remote Config for updating rules without publishing a new app and Analytics to understand user engagement.

## Building the Project
1. Install Android Studio and ensure the Android SDK is available.
2. Clone this repository and open it in Android Studio.
3. Place your `google-services.json` file under `app/` and run Gradle sync so Firebase dependencies are resolved.
4. Connect a device or start an emulator and press **Run**.

The project uses Gradle. Unit tests can be executed with `./gradlew test` (note: on some systems additional Java setup may be required).

## Contributing
Pull requests are welcome! Open an issue first to discuss major changes. For new features please also include documentation updates so other developers can quickly understand the workflow.

