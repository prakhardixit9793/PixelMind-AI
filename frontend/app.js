const API_BASE = "https://pixelmind-ai-1.onrender.com";

const sections = document.querySelectorAll(".section");
const navLinks = document.querySelectorAll(".nav-link");

function updateActiveNav() {
    let current = "";
    sections.forEach((section) => {
        const top = section.offsetTop - 200;
        if (window.scrollY >= top) {
            current = section.getAttribute("id");
        }
    });

    navLinks.forEach((link) => {
        link.classList.remove("active");
        if (link.getAttribute("href") === "#" + current) {
            link.classList.add("active");
        }
    });
}

window.addEventListener("scroll", updateActiveNav);

function scrollToSection(id) {
    document.getElementById(id).scrollIntoView({ behavior: "smooth" });
}

function showToast(message, type = "error") {
    const existing = document.querySelector(".toast");
    if (existing) existing.remove();

    const toast = document.createElement("div");
    toast.className = `toast ${type}`;
    toast.textContent = message;
    document.body.appendChild(toast);

    setTimeout(() => {
        toast.style.opacity = "0";
        toast.style.transform = "translateX(40px)";
        toast.style.transition = "all 0.3s ease-out";
        setTimeout(() => toast.remove(), 300);
    }, 4000);
}

function setLoading(btnId, loaderId, loading) {
    const btn = document.getElementById(btnId);
    const loader = document.getElementById(loaderId);
    const btnText = btn.querySelector(".btn-text");

    if (loading) {
        btn.disabled = true;
        loader.classList.add("active");
        btnText.classList.add("hidden");
    } else {
        btn.disabled = false;
        loader.classList.remove("active");
        btnText.classList.remove("hidden");
    }
}

async function sendChat() {
    const input = document.getElementById("chat-prompt");
    const prompt = input.value.trim();
    if (!prompt) return;

    const container = document.getElementById("chat-messages");

    const userMsg = document.createElement("div");
    userMsg.className = "chat-msg user";
    userMsg.innerHTML = `
        <div class="msg-avatar">You</div>
        <div class="msg-content">${escapeHtml(prompt)}</div>
    `;
    container.appendChild(userMsg);

    input.value = "";
    container.scrollTop = container.scrollHeight;

    const thinkingMsg = document.createElement("div");
    thinkingMsg.className = "chat-msg ai";
    thinkingMsg.id = "thinking-msg";
    thinkingMsg.innerHTML = `
        <div class="msg-avatar">✦</div>
        <div class="msg-content">
            <div class="thinking-dots"><span></span><span></span><span></span></div>
        </div>
    `;
    container.appendChild(thinkingMsg);
    container.scrollTop = container.scrollHeight;

    setLoading("send-chat-btn", "chat-loader", true);

    try {
        const params = new URLSearchParams({ prompt: prompt });
        const response = await fetch(`${API_BASE}/ask-ai?${params}`);

        if (!response.ok) throw new Error(`Server returned ${response.status}`);

        const text = await response.text();

        const thinking = document.getElementById("thinking-msg");
        if (thinking) thinking.remove();

        const aiMsg = document.createElement("div");
        aiMsg.className = "chat-msg ai";
        aiMsg.innerHTML = `
            <div class="msg-avatar">✦</div>
            <div class="msg-content">${escapeHtml(text)}</div>
        `;
        container.appendChild(aiMsg);
        container.scrollTop = container.scrollHeight;
    } catch (err) {
        console.error("Chat error:", err);
        const thinking = document.getElementById("thinking-msg");
        if (thinking) thinking.remove();

        showToast("Failed to get AI response. Check backend connection.");
    } finally {
        setLoading("send-chat-btn", "chat-loader", false);
    }
}

async function createRecipe() {
    const ingredients = document
        .getElementById("recipe-ingredients")
        .value.trim();
    if (!ingredients) {
        showToast("Please enter some ingredients.");
        return;
    }

    const cuisine = document.getElementById("recipe-cuisine").value;
    const dietary = document.getElementById("recipe-dietary").value;

    setLoading("create-recipe-btn", "recipe-loader", true);

    try {
        const params = new URLSearchParams({
            ingredients: ingredients,
            cuisine: cuisine,
            dietaryRestriction: dietary,
        });

        const response = await fetch(`${API_BASE}/recipe-creator?${params}`);

        if (!response.ok) throw new Error(`Server returned ${response.status}`);

        const text = await response.text();

        document.getElementById("recipe-empty").style.display = "none";
        const display = document.getElementById("recipe-display");
        display.style.display = "block";
        document.getElementById("recipe-content").textContent = text;

        showToast("Recipe created!", "success");
    } catch (err) {
        console.error("Recipe error:", err);
        showToast(
            "Failed to create recipe. Make sure the backend is running."
        );
    } finally {
        setLoading("create-recipe-btn", "recipe-loader", false);
    }
}

function escapeHtml(str) {
    const div = document.createElement("div");
    div.textContent = str;
    return div.innerHTML;
}

const observerOptions = {
    threshold: 0.1,
    rootMargin: "0px 0px -50px 0px",
};

const observer = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
        if (entry.isIntersecting) {
            entry.target.style.opacity = "1";
            entry.target.style.transform = "translateY(0)";
        }
    });
}, observerOptions);

document.querySelectorAll(".tool-card").forEach((card) => {
    card.style.opacity = "0";
    card.style.transform = "translateY(30px)";
    card.style.transition = "opacity 0.6s ease-out, transform 0.6s ease-out";
    observer.observe(card);
});

document
    .getElementById("recipe-ingredients")
    .addEventListener("keydown", function (e) {
        if (e.key === "Enter") createRecipe();
    });
