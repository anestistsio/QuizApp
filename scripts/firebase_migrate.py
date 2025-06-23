import argparse
import os
import json
import requests

BASE_URL = "https://quizapp-41598-default-rtdb.europe-west1.firebasedatabase.app"

CATEGORIES = {
    'National': 'NationalTable.txt',
    'General': 'GeneralTable.txt',
    'Clubs': 'ClubsTable.txt',
    'Geography': 'GeographyTable.txt',
    'Εθνικές': 'GreekNationalTable.txt',
    'Γενικές': 'GreekGeneralTable.txt',
    'Συλλόγοι': 'GreekClubsTable.txt',
    'Γεωγραφία': 'GreekGeographyTable.txt',
}

def load_questions(path):
    questions = []
    with open(path, 'r', encoding='utf-8') as f:
        for line in f:
            parts = line.strip().split('|')
            if len(parts) != 4:
                continue
            qid, question, answer, displayed = parts
            questions.append({
                'id': int(qid),
                'question': question,
                'answer': answer,
                'displayed': displayed.lower() == 'true'
            })
    return questions


def upload_category(session, category, questions, auth=None):
    url = f"{BASE_URL}/questions/{category}.json"
    if auth:
        url += f"?auth={auth}"
    payload = {str(q['id']): q for q in questions}
    resp = session.put(url, json=payload)
    resp.raise_for_status()
    print(f"Uploaded {len(questions)} items to {category}")


def main():
    parser = argparse.ArgumentParser(description="Upload local asset questions to Firebase Realtime Database")
    parser.add_argument('--assets', default='app/src/main/assets', help='Path to assets directory')
    parser.add_argument('--auth', help='Firebase Database secret or auth token')
    args = parser.parse_args()

    session = requests.Session()

    for category, filename in CATEGORIES.items():
        path = os.path.join(args.assets, filename)
        qs = load_questions(path)
        upload_category(session, category, qs, auth=args.auth)


if __name__ == '__main__':
    main()
