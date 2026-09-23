let machines = [];

let charts = {};


/* ================= START ================= */

document.addEventListener(
    "DOMContentLoaded",
    function() {

        const user = getUser();

        if (!user) {
            window.location.href =
                "login.html";
            return;
        }

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
            m => m.operationalStatus === "RUNNING"
        ).length;


    const maintenance =
        machines.filter(
            m => m.operationalStatus === "MAINTENANCE"
        ).length;


    const offline =
        machines.filter(
            m => m.operationalStatus === "OFFLINE"
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
                    machine.operationalStatus || ""
                ).toLowerCase();


            container.innerHTML += `

                <div class="col-md-4">

                    <div class="machine-card">

                        <div class="machine-code">
                            ${machine.machineCode}
                        </div>


                        <div class="machine-name">
                            ${machine.machineName}
                        </div>


                        <p class="mt-2">
                            Type:
                            ${machine.machineType || "-"}
                        </p>


                        <p>
                            Location:
                            ${machine.location || "-"}
                        </p>


                        <span
                            class="status status-${status}">

                            ${machine.operationalStatus}

                        </span>


                        <br>


                        <button
                            class="btn btn-primary btn-sm mt-3"
                            onclick="viewDigitalTwin(${machine.machineId})">

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
                    machine.operationalStatus || ""
                ).toLowerCase();


            table.innerHTML += `

                <tr>

                    <td>
                        ${machine.machineCode}
                    </td>

                    <td>
                        ${machine.machineName}
                    </td>

                    <td>
                        ${machine.machineType || "-"}
                    </td>

                    <td>
                        ${machine.location || "-"}
                    </td>

                    <td>

                        <span
                            class="status status-${status}">

                            ${machine.operationalStatus}

                        </span>

                    </td>

                    <td>

                        <button
                            class="btn btn-primary btn-sm"
                            onclick="viewDigitalTwin(${machine.machineId})">

                            View

                        </button>

                    </td>

                </tr>

            `;

        }
    );

}


/* ================= DIGITAL TWIN ================= */

async function viewDigitalTwin(machineId) {

    try {

        // Get machine information and machine code
        const machineData =
            await apiRequest(
                "/dashboard/" + machineId
            );

        // Run Member 2 Digital Twin prediction
        const prediction =
            await apiRequest(
                "/digital-twin/predict/" +
                machineData.machineCode
            );

        alert(
            "DIGITAL TWIN PREDICTION\n\n" +

            "Machine: " +
            prediction.machineCode +
            "\n" +

            prediction.machineName +
            "\n\n" +

            "Temperature: " +
            prediction.temperature +
            " °C\n\n" +

            "Vibration: " +
            prediction.vibration +
            " mm/s\n\n" +

            "RPM: " +
            prediction.rpm +
            "\n\n" +

            "Health Score: " +
            prediction.healthScore +
            "%\n\n" +

            "Status: " +
            prediction.status +
            "\n\n" +

            "DM-TVC Coupling Factor: " +
            prediction.harmonicCouplingFactor +
            "\n\n" +

            "Degradation Velocity: " +
            prediction.degradationVelocity +
            "\n\n" +

            "Estimated Cycles to Failure: " +
            prediction.estimatedCyclesToFailure
        );

    }

    catch (error) {

        console.error(
            "Digital Twin prediction error:",
            error
        );

        alert(
            "Unable to load Digital Twin prediction."
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
                    value="${machine.machineId}">

                    ${machine.machineCode}

                    -

                    ${machine.machineName}

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
                "/sensor-readings/machine/" +
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
                                reading.readingTime
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

                            ${reading.speedRpm}

                        </td>

                    </tr>

                `;

            }
        );

    }

    catch (error) {

        console.error(error);

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
                    value="${machine.machineId}">

                    ${machine.machineCode}

                    -

                    ${machine.machineName}

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
                "/sensor-readings/machine/" +
                machineId
            );


        const ordered =
            [...readings];


        const labels =
            ordered.map(
                r =>
                    new Date(
                        r.readingTime
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
            "Speed (RPM)",
            labels,
            ordered.map(
                r => r.speedRpm
            )
        );

    }

    catch (error) {

        console.error(error);

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