/* =============================================
   PixelMind AI — Application Logic
   ============================================= */

// -------------------------------------------------------------------
// Configuration — Update API_BASE to your Render backend URL
// -------------------------------------------------------------------
const API_BASE = "https://pixelmind-ai-1.onrender.com";

// -------------------------------------------------------------------
// Navigation — Active tab highlighting on scroll
// -------------------------------------------------------------------
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

// -------------------------------------------------------------------
// Toast Notifications
// -------------------------------------------------------------------
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

// -------------------------------------------------------------------
// Loading State Helpers
// -------------------------------------------------------------------
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

// -------------------------------------------------------------------
// Image Generation
// -------------------------------------------------------------------
async function generateImage() {
    const prompt = document.getElementById("image-prompt").value.trim();
    if (!prompt) {
        showToast("Please enter a prompt to generate an image.");
        return;
    }

    const size = document.getElementById("image-size").value;

    setLoading("generate-image-btn", "image-loader", true);

    try {
        const params = new URLSearchParams({
            prompt: prompt,
            n: 1,
            width: size,
            height: size,
        });

        const response = await fetch(`${API_BASE}/generate-image?${params}`);

        if (!response.ok) {
            throw new Error(`Server returned ${response.status}`);
        }

        const blob = await response.blob();
        const imageUrl = URL.createObjectURL(blob);

        document.getElementById("image-empty").style.display = "none";
        const display = document.getElementById("image-display");
        display.style.display = "block";

        const img = document.getElementById("generated-image");
        img.src = imageUrl;
        img.alt = prompt;

        showToast("Image generated successfully!", "success");
    } catch (err) {
        console.error("Image generation error:", err);
        showToast(
            "Failed to generate image. Make sure the backend is running."
        );
    } finally {
        setLoading("generate-image-btn", "image-loader", false);
    }
}

// -------------------------------------------------------------------
// Download Image
// -------------------------------------------------------------------
function downloadImage() {
    const img = document.getElementById("generated-image");
    if (!img.src) return;

    const a = document.createElement("a");
    a.href = img.src;
    a.download = "pixelmind-ai-" + Date.now() + ".png";
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);

    showToast("Image downloaded!", "success");
}

// -------------------------------------------------------------------
// AI Chat
// -------------------------------------------------------------------
async function sendChat() {
    const input = document.getElementById("chat-prompt");
    const prompt = input.value.trim();
    if (!prompt) return;

    const container = document.getElementById("chat-messages");

    // Add user message
    const userMsg = document.createElement("div");
    userMsg.className = "chat-msg user";
    userMsg.innerHTML = `
        <div class="msg-avatar">You</div>
        <div class="msg-content">${escapeHtml(prompt)}</div>
    `;
    container.appendChild(userMsg);

    input.value = "";
    container.scrollTop = container.scrollHeight;

    // Add thinking indicator
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

        // Remove thinking indicator
        const thinking = document.getElementById("thinking-msg");
        if (thinking) thinking.remove();

        // Add AI response
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

// -------------------------------------------------------------------
// Recipe Creator
// -------------------------------------------------------------------
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

// -------------------------------------------------------------------
// Utility
// -------------------------------------------------------------------
function escapeHtml(str) {
    const div = document.createElement("div");
    div.textContent = str;
    return div.innerHTML;
}

// -------------------------------------------------------------------
// Intersection Observer for scroll animations
// -------------------------------------------------------------------
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

// Allow Enter key on image prompt
document
    .getElementById("image-prompt")
    .addEventListener("keydown", function (e) {
        if (e.key === "Enter") generateImage();
    });

// Allow Enter key on recipe ingredients
document
    .getElementById("recipe-ingredients")
    .addEventListener("keydown", function (e) {
        if (e.key === "Enter") createRecipe();
    });
