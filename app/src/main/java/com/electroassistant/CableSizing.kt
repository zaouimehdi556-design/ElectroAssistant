package com.electroassistant

import kotlin.math.sqrt

data class CableSizingInput(
    val material: Material,
    val phase: Phase,
    val loadType: LoadType,
    val current: Double,
    val length: Double,
    val maxVoltageDropPercent: Double,
    val cosPhi: Double,
    val installationMethod: InstallationMethod,
    val insulation: Insulation,
    val loadedConductors: Int,
    val ambientTemperature: Double,
    val groupedCircuits: Int
)

enum class Material {
    COPPER,
    ALUMINIUM
}

enum class Phase {
    SINGLE_PHASE,
    THREE_PHASE
}

enum class LoadType {
    LIGHTING,
    POWER,
    MOTOR
}

enum class InstallationMethod {
    A1,
    A2,
    B1,
    B2,
    C,
    D1,
    E,
    F
}

enum class Insulation {
    PVC,
    XLPE
}

data class CableSizingResult(
    val section: Double,
    val voltageDropVolts: Double,
    val voltageDropPercent: Double,
    val resistance: Double,
    val correctionFactor: Double,
    val warning: String?
)

fun calculateCableSizing(
    input: CableSizingInput
): CableSizingResult? {

    if (
        input.current <= 0 ||
        input.length <= 0 ||
        input.cosPhi <= 0 ||
        input.cosPhi > 1 ||
        input.maxVoltageDropPercent <= 0 ||
        input.ambientTemperature < -20 ||
        input.groupedCircuits < 1
    ) {
        return null
    }

    val sections = listOf(
        1.5,
        2.5,
        4.0,
        6.0,
        10.0,
        16.0,
        25.0,
        35.0,
        50.0,
        70.0,
        95.0,
        120.0,
        150.0,
        185.0,
        240.0
    )

    /*
     * Résistivité à 20 °C.
     *
     * Cuivre ≈ 0.0175 Ω·mm²/m
     * Aluminium ≈ 0.0282 Ω·mm²/m
     */
    val rho =
        if (input.material == Material.COPPER) {
            0.0175
        } else {
            0.0282
        }

    /*
     * Facteur température simplifié.
     *
     * 30 °C = 1.00
     *
     * Pour une étude normative complète,
     * les facteurs doivent être sélectionnés
     * dans les tableaux correspondant à
     * l'installation réelle.
     */
    val temperatureFactor =
        when (input.insulation) {

            Insulation.PVC -> when {
                input.ambientTemperature <= 30 -> 1.0
                input.ambientTemperature <= 35 -> 0.94
                input.ambientTemperature <= 40 -> 0.87
                input.ambientTemperature <= 45 -> 0.79
                input.ambientTemperature <= 50 -> 0.71
                input.ambientTemperature <= 55 -> 0.61
                input.ambientTemperature <= 60 -> 0.50
                else -> 0.0
            }

            Insulation.XLPE -> when {
                input.ambientTemperature <= 30 -> 1.0
                input.ambientTemperature <= 35 -> 0.96
                input.ambientTemperature <= 40 -> 0.91
                input.ambientTemperature <= 45 -> 0.87
                input.ambientTemperature <= 50 -> 0.82
                input.ambientTemperature <= 55 -> 0.76
                input.ambientTemperature <= 60 -> 0.71
                input.ambientTemperature <= 65 -> 0.65
                input.ambientTemperature <= 70 -> 0.58
                input.ambientTemperature <= 75 -> 0.50
                input.ambientTemperature <= 80 -> 0.41
                else -> 0.0
            }
        }

    if (temperatureFactor <= 0) {
        return null
    }

    /*
     * Facteur de regroupement.
     *
     * Valeurs simplifiées correspondant à
     * quelques configurations courantes.
     */
    val groupingFactor = when {
        input.groupedCircuits <= 1 -> 1.00
        input.groupedCircuits == 2 -> 0.80
        input.groupedCircuits == 3 -> 0.70
        input.groupedCircuits == 4 -> 0.65
        input.groupedCircuits <= 6 -> 0.57
        input.groupedCircuits <= 9 -> 0.50
        input.groupedCircuits <= 12 -> 0.45
        else -> 0.40
    }

    val correctionFactor =
        temperatureFactor * groupingFactor

    /*
     * Courants de référence simplifiés.
     *
     * Ces valeurs servent ici de pré-dimensionnement.
     * La validation finale doit utiliser le tableau
     * exact correspondant au mode de pose.
     */
    val baseCurrent = when (input.material) {

        Material.COPPER -> when (input.installationMethod) {

            InstallationMethod.A1 -> listOf(
                1.5 to 14.0,
                2.5 to 18.5,
                4.0 to 25.0,
                6.0 to 32.0,
                10.0 to 44.0,
                16.0 to 59.0,
                25.0 to 77.0,
                35.0 to 96.0,
                50.0 to 117.0,
                70.0 to 149.0,
                95.0 to 179.0,
                120.0 to 206.0,
                150.0 to 236.0,
                185.0 to 268.0,
                240.0 to 315.0
            )

            InstallationMethod.B1 -> listOf(
                1.5 to 17.5,
                2.5 to 24.0,
                4.0 to 32.0,
                6.0 to 41.0,
                10.0 to 57.0,
                16.0 to 76.0,
                25.0 to 101.0,
                35.0 to 125.0,
                50.0 to 150.0,
                70.0 to 192.0,
                95.0 to 232.0,
                120.0 to 269.0,
                150.0 to 309.0,
                185.0 to 353.0,
                240.0 to 415.0
            )

            else -> listOf(
                1.5 to 18.5,
                2.5 to 25.0,
                4.0 to 34.0,
                6.0 to 43.0,
                10.0 to 60.0,
                16.0 to 80.0,
                25.0 to 106.0,
                35.0 to 131.0,
                50.0 to 158.0,
                70.0 to 200.0,
                95.0 to 242.0,
                120.0 to 280.0,
                150.0 to 320.0,
                185.0 to 366.0,
                240.0 to 430.0
            )
        }

        Material.ALUMINIUM -> when (input.installationMethod) {

            InstallationMethod.A1 -> listOf(
                2.5 to 14.0,
                4.0 to 19.0,
                6.0 to 24.0,
                10.0 to 33.0,
                16.0 to 43.0,
                25.0 to 56.0,
                35.0 to 69.0,
                50.0 to 84.0,
                70.0 to 107.0,
                95.0 to 128.0,
                120.0 to 147.0,
                150.0 to 169.0,
                185.0 to 192.0,
                240.0 to 227.0
            )

            else -> listOf(
                2.5 to 18.0,
                4.0 to 24.0,
                6.0 to 30.0,
                10.0 to 41.0,
                16.0 to 54.0,
                25.0 to 70.0,
                35.0 to 86.0,
                50.0 to 103.0,
                70.0 to 132.0,
                95.0 to 158.0,
                120.0 to 182.0,
                150.0 to 209.0,
                185.0 to 237.0,
                240.0 to 280.0
            )
        }
    }

    for (section in sections) {

        val referenceCurrent =
            baseCurrent.firstOrNull {
                it.first == section
            }?.second ?: continue

        val admissibleCurrent =
            referenceCurrent * correctionFactor

        if (input.current > admissibleCurrent) {
            continue
        }

        /*
         * Résistance de la boucle.
         *
         * Monophasé :
         * 2 × L
         *
         * Triphasé :
         * √3 × L
         */
        val resistance =
            rho * input.length / section

        val voltageDrop =
            if (input.phase == Phase.THREE_PHASE) {

                sqrt(3.0) *
                        input.current *
                        resistance *
                        input.cosPhi

            } else {

                2.0 *
                        input.current *
                        resistance *
                        input.cosPhi
            }

        val nominalVoltage =
            if (input.phase == Phase.THREE_PHASE) {
                400.0
            } else {
                230.0
            }

        val voltageDropPercent =
            voltageDrop /
                    nominalVoltage *
                    100.0

        if (voltageDropPercent <= input.maxVoltageDropPercent) {

            val warning =
                if (input.groupedCircuits > 1 ||
                    input.ambientTemperature != 30.0
                ) {
                    "⚠️ Facteurs de correction appliqués. Vérification normative finale recommandée."
                } else {
                    null
                }

            return CableSizingResult(
                section = section,
                voltageDropVolts = voltageDrop,
                voltageDropPercent = voltageDropPercent,
                resistance = resistance,
                correctionFactor = correctionFactor,
                warning = warning
            )
        }
    }

    return null
}
