const API_BASE = "http://localhost:8080/api";


async function apiRequest(url, options = {}) {

    const headers = {
        "Content-Type": "application/json",
        ...(options.headers || {})
    };

    const response = await fetch(
        API_BASE + url,
        {
            ...options,
            headers: headers,
            credentials: "include"
        }
    );


    if (
        response.status === 401 ||
        response.status === 403
    ) {

        localStorage.removeItem("dt_user");

        window.location.href =
            "login.html";

        return;
    }


    if (!response.ok) {

        throw new Error(
            "API request failed: " +
            response.status
        );

    }


    return response.json();
}



/* ================= LOGIN ================= */

async function loginUser(
    username,
    password
) {

    const response = await fetch(
        API_BASE + "/auth/login",
        {

            method: "POST",

            headers: {
                "Content-Type":
                    "application/json"
            },

            credentials: "include",

            body: JSON.stringify({

                username: username,

                password: password

            })

        }
    );


    if (!response.ok) {

        throw new Error(
            "Invalid username or password"
        );

    }


    const data =
        await response.json();


    localStorage.setItem(
        "dt_user",
        JSON.stringify(data)
    );


    return data;
}



/* ================= LOGOUT ================= */

function logout() {

    localStorage.removeItem("dt_user");

    window.location.href =
        "login.html";
}



/* ================= USER ================= */

function getUser() {

    return JSON.parse(
        localStorage.getItem(
            "dt_user"
        )
    );

}