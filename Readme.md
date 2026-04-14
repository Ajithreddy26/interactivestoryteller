# Storyteller

I bought a book I really wanted to read and never got around to it. Every day driving to office I'd just put on the radio. Then one day a friend called me while I was driving and just... told me a story. Kept me hooked the whole commute. That's when it hit me — I could build something that does exactly that.

Storyteller is a Spring Boot app that narrates books to you using an LLM, streaming the narration in real time. The goal is to eventually make it interactive — so you can ask questions mid-story without getting spoilers.

Still a work in progress. Learning as I go.

---

## How it works

The backend calls Ollama (llama3.2 locally) via Spring AI and streams the response token by token using SSE. The frontend just listens to the stream and appends each token as it arrives. No waiting for the full response.

The prompt tells the AI to narrate like it's talking to someone who's driving — no stage directions, no asterisks, just words you'd actually say out loud.

---

## Stack

- Java 21, Spring Boot 4
- Spring AI 2.0 (Ollama integration)
- SSE for streaming
- H2 in-memory DB + Spring Data JPA
- Plain HTML/JS frontend

---

## Running it locally

You'll need Ollama installed and llama3.2 pulled:

```bash
ollama pull llama3.2
ollama serve
```

Then:

```bash
git clone https://github.com/Ajithreddy26/interactivestoryteller.git
cd storyteller
./mvnw spring-boot:run
```

Open `Frontend/app.html` in your browser.

---

## What's next

- Text-to-speech so it actually reads out loud
- Ask questions mid-story (with spoiler prevention based on how far you've listened)
- Track progress so you can resume where you left off
- Hands-free mode for driving
