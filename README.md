# QuizApp
ABSTRACT

In this thesis, which was carried out at the Department of Electrical and
Computer Engineering (ECE) of the University of Peloponnese, which
belongs to the School of Engineering based in Patras, an Android
application was developed with the aim of conducting a group Quiz on
football knowledge. In this app, two teams are pitted against each other
in a challenging Quiz, aiming to achieve victory. It is necessary to have at
least two (2) people to start the Quiz. At the conclusion of the Quiz, only
one winning team emerges, but knowledge and fun are always shared
among all participants.
The reasons that led us to choose this topic are the lack of a two-team
football Quiz Android application in the Greek market, along with our
great interest in the Android platform whose development has been rapid
in recent years and covers a very large part of the market for
smartphones, tablets, wearables, televisions and other technological
media.

Link : https://drive.google.com/file/d/1GJWAkX12505LVzpWwwmJjMPMocFVHsO6/view?usp=drive_link

## Firebase Realtime Database Setup

This project can store quiz questions in Firebase Realtime Database instead of the local SQLite database.

1. [Create a Firebase project](https://console.firebase.google.com/), enable *Realtime Database*, and choose the location `europe-west1`.
2. Download the updated `google-services.json` file and place it under `app/` (this repository already includes an example configured for `quizapp-41598`).
3. In the Firebase console open **Realtime Database → Rules** and, for development, allow read/write access:

```json
{
  "rules": {
    ".read": true,
    ".write": true
  }
}
```

   Restrict these rules before releasing the app.
4. Run Gradle sync so the `firebase-database` dependency is resolved.
5. Use the migration script to upload the existing questions:

```bash
python scripts/firebase_migrate.py --auth YOUR_DATABASE_SECRET
```

Replace `YOUR_DATABASE_SECRET` with an auth token or remove the option if your rules are public.

The questions will be uploaded under `questions/<CATEGORY>/<ID>` in the database specified by the `firebase_url` in `google-services.json`.

