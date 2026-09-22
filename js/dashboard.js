let machines = [];

let charts = {};



/* ================= START ================= */

document.addEventListener(
    "DOMContentLoaded",
    function() {

        const token =
            localStorage.getItem(
                "dt_token"
            );


        if (!token) {

            window.location.href =
                "login.html";

            return;
        }


        const user =
            getUser();


        if (user) {

            document.getElementById(
                "loggedUser"
            ).innerText =
                user.username +
                " (" +
                user.role +
                ")";

        }


        loadMachines();

    }
);



/* ================= PAGE NAVIGATION ================= */

function showPage(
    pageId,
    button
) {

    const pages =
        document.querySelectorAll(
            ".main-content section"
        );


    pages.forEach(
        page => {

            page.classList.add(
                "hidden-page"
            );

        }
    );


    document.getElementById(
        pageId
    ).classList.remove(
        "hidden-page"
    );


    document.querySelectorAll(
        ".sidebar-btn"
    ).forEach(
        btn => {

            btn.classList.remove(
                "active"
            );

        }
    );


    button.classList.add(
        "active"
    );


    if (pageId === "machinesPage") {

        displayMachineTable();

    }


    if (pageId === "reportsPage") {

        prepareReportMachines();

    }


    if (pageId === "chartsPage") {

        prepareChartMachines();

    }

}



/* ================= LOAD MACHINES ================= */

async function loadMachines() {

    try {

        machines =
            await apiRequest(
                "/machines"
            );


        updateDashboard();


        displayMachineCards();


        displayMachineTable();


        prepareReportMachines();


        prepareChartMachines();

    }

    catch (error) {

        console.error(error);

        alert(
            "Unable to load machines. " +
            "Make sure Spring Boot backend is running."
        );

    }

}



/* ================= DASHBOARD ================= */

function updateDashboard() {

    const total =
        machines.length;


    const running =
        machines.filter(
            m => m.status === "RUNNING"
        ).length;


    const maintenance =
        machines.filter(
            m => m.status === "MAINTENANCE"
        ).length;


    const offline =
        machines.filter(
            m => m.status === "OFFLINE"
        ).length;


    document.getElementById(
        "totalMachines"
    ).innerText = total;


    document.getElementById(
        "runningMachines"
    ).innerText = running;


    document.getElementById(
        "maintenanceMachines"
    ).innerText =
        maintenance;


    document.getElementById(
        "offlineMachines"
    ).innerText =
        offline;

}



/* ================= MACHINE CARDS ================= */

function displayMachineCards() {

    const container =
        document.getElementById(
            "dashboardMachines"
        );


    container.innerHTML = "";


    if (machines.length === 0) {

        container.innerHTML =
            `<div class="col-12">
                No machines available.
            </div>`;

        return;

    }


    machines.forEach(
        machine => {

            const status =
                String(
                    machine.status || ""
                ).toLowerCase();


            container.innerHTML += `

                <div class="col-md-4">

                    <div class="machine-card">

                        <div class="machine-code">

                            ${machine.machineCode}

                        </div>


                        <div class="machine-name">

                            ${machine.name}

                        </div>


                        <p class="mt-2">

                            Model:
                            ${machine.model || "-"}

                        </p>


                        <p>

                            Location:
                            ${machine.location || "-"}

                        </p>


                        <span
                            class="status status-${status}">

                            ${machine.status}

                        </span>


                        <br>


                        <button
                            class="btn btn-primary btn-sm mt-3"
                            onclick="viewDigitalTwin(${machine.id})">

                            View Digital Twin

                        </button>

                    </div>

                </div>

            `;

        }
    );

}



/* ================= MACHINE TABLE ================= */

function displayMachineTable() {

    const table =
        document.getElementById(
            "machineTable"
        );


    table.innerHTML = "";


    machines.forEach(
        machine => {

            const status =
                String(
                    machine.status || ""
                ).toLowerCase();


            table.innerHTML += `

                <tr>

                    <td>
                        ${machine.machineCode}
                    </td>

                    <td>
                        ${machine.name}
                    </td>

                    <td>
                        ${machine.model || "-"}
                    </td>

                    <td>
                        ${machine.location || "-"}
                    </td>

                    <td>

                        <span
                            class="status status-${status}">

                            ${machine.status}

                        </span>

                    </td>

                    <td>

                        <button
                            class="btn btn-primary btn-sm"
                            onclick="viewDigitalTwin(${machine.id})">

                            View

                        </button>

                    </td>

                </tr>

            `;

        }
    );

}



/* ================= DIGITAL TWIN ================= */

async function viewDigitalTwin(
    machineId
) {

    try {

        const data =
            await apiRequest(
                "/twins/" + machineId
            );


        alert(

            "DIGITAL TWIN\n\n" +

            "Machine: " +
            data.machineCode +
            "\n\n" +

            "Health Score: " +
            data.healthScore +
            "%\n\n" +

            "Temperature: " +
            data.temperature +
            " °C\n\n" +

            "Vibration: " +
            data.vibration +
            "\n\n" +

            "Energy Consumption: " +
            data.energyConsumption +
            "\n\n" +

            "Production Rate: " +
            data.productionRate +
            "\n\n" +

            "Efficiency: " +
            data.calculatedEfficiencyPercent +
            "%\n\n" +

            "Prediction: " +
            data.prediction

        );

    }

    catch (error) {

        alert(
            "Unable to load Digital Twin data."
        );

    }

}



/* ================= REPORT MACHINE ================= */

function prepareReportMachines() {

    const select =
        document.getElementById(
            "reportMachine"
        );


    select.innerHTML =
        `<option value="">
            Select Machine
        </option>`;


    machines.forEach(
        machine => {

            select.innerHTML += `

                <option
                    value="${machine.id}">

                    ${machine.machineCode}
                    -
                    ${machine.name}

                </option>

            `;

        }
    );

}



/* ================= REPORT ================= */

async function loadReport() {

    const machineId =
        document.getElementById(
            "reportMachine"
        ).value;


    if (!machineId) {

        return;

    }


    try {

        const readings =
            await apiRequest(
                "/sensors/machine/" +
                machineId
            );


        const table =
            document.getElementById(
                "reportTable"
            );


        table.innerHTML = "";


        readings.forEach(
            reading => {

                table.innerHTML += `

                    <tr>

                        <td>
                            ${new Date(
                                reading.recordedAt
                            ).toLocaleString()}
                        </td>

                        <td>
                            ${reading.temperature}
                            °C
                        </td>

                        <td>
                            ${reading.vibration}
                        </td>

                        <td>
                            ${reading.energyConsumption}
                        </td>

                        <td>
                            ${reading.productionRate}
                        </td>

                    </tr>

                `;

            }
        );

    }

    catch (error) {

        alert(
            "Unable to load report."
        );

    }

}



/* ================= CHART MACHINES ================= */

function prepareChartMachines() {

    const select =
        document.getElementById(
            "chartMachine"
        );


    select.innerHTML =
        `<option value="">
            Select Machine
        </option>`;


    machines.forEach(
        machine => {

            select.innerHTML += `

                <option
                    value="${machine.id}">

                    ${machine.machineCode}
                    -
                    ${machine.name}

                </option>

            `;

        }
    );

}



/* ================= LOAD CHARTS ================= */

async function loadCharts() {

    const machineId =
        document.getElementById(
            "chartMachine"
        ).value;


    if (!machineId) {

        return;

    }


    try {

        const readings =
            await apiRequest(
                "/sensors/machine/" +
                machineId
            );


        const ordered =
            [...readings].reverse();


        const labels =
            ordered.map(
                r =>
                    new Date(
                        r.recordedAt
                    ).toLocaleTimeString()
            );


        createChart(
            "temperatureChart",
            "Temperature",
            labels,
            ordered.map(
                r => r.temperature
            )
        );


        createChart(
            "vibrationChart",
            "Vibration",
            labels,
            ordered.map(
                r => r.vibration
            )
        );


        createChart(
            "energyChart",
            "Energy Consumption",
            labels,
            ordered.map(
                r => r.energyConsumption
            )
        );


        createChart(
            "productionChart",
            "Production Rate",
            labels,
            ordered.map(
                r => r.productionRate
            )
        );

    }

    catch (error) {

        alert(
            "Unable to load chart data."
        );

    }

}



/* ================= CREATE CHART ================= */

function createChart(
    canvasId,
    label,
    labels,
    values
) {

    if (charts[canvasId]) {

        charts[canvasId].destroy();

    }


    const canvas =
        document.getElementById(
            canvasId
        );


    charts[canvasId] =
        new Chart(
            canvas,
            {

                type: "line",

                data: {

                    labels: labels,

                    datasets: [

                        {

                            label: label,

                            data: values,

                            borderWidth: 2,

                            tension: 0.3,

                            fill: false

                        }

                    ]

                },

                options: {

                    responsive: true,

                    scales: {

                        y: {

                            beginAtZero: true

                        }

                    }

                }

            }
        );

}



/* ================= AUTO REFRESH ================= */

setInterval(
    function() {

        loadMachines();

    },
    30000
);