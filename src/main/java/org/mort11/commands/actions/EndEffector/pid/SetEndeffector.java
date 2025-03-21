package org.mort11.commands.actions.endeffector.pid;

import org.mort11.config.constants.PhysicalConstants;
import org.mort11.subsystems.TikiTorchArm;
import org.mort11.subsystems.Elevator;

import static org.mort11.config.constants.PIDConstants.Elevator.MEDIUM_MAX_ELEVATOR_SPEED;
import static org.mort11.config.constants.PIDConstants.Elevator.SLOW_MAX_ELEVATOR_SPEED;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import static org.mort11.config.constants.PhysicalConstants.Elevator.*;
import static org.mort11.config.constants.PhysicalConstants.TikiTorchArm.*;
import static org.mort11.config.constants.PhysicalConstants.AlgaeArm.*;

public class SetEndeffector extends SequentialCommandGroup {

    public SetEndeffector(double elevatorPos, double tikiArmPos, double algaeArmPos) {

        addCommands(
            new SequentialCommandGroup(
                new ParallelCommandGroup(
                    SetTikiTorchArm.algaeClear(),
                    SetAlgaeArm.rest()
                ).withTimeout(0.5),

                new ParallelCommandGroup(
                    new Elevate(elevatorPos),
                    new SequentialCommandGroup(
                        new ParallelCommandGroup(
                            SetTikiTorchArm.algaeClear(),
                            SetAlgaeArm.rest()
                        ).withTimeout(0.75),
                        new ParallelCommandGroup(
                            new SetAlgaeArm(algaeArmPos),
                            new SetTikiTorchArm(tikiArmPos)
                        )
                    )
                )
            )
        );
    }

    public SetEndeffector(double elevatorPos, double tikiArmPos, double algaeArmPos, double elevatorSpeed) {

        addCommands(
            new SequentialCommandGroup(
                new ParallelCommandGroup(
                    SetTikiTorchArm.algaeClear(),
                    SetAlgaeArm.rest()
                ).withTimeout(0.5),

                new ParallelCommandGroup(
                    new Elevate(elevatorPos, elevatorSpeed),
                    new SequentialCommandGroup(
                        new ParallelCommandGroup(
                            SetTikiTorchArm.algaeClear(),
                            SetAlgaeArm.rest()
                        ).withTimeout(0.75),
                        new ParallelCommandGroup(
                            new SetAlgaeArm(algaeArmPos),
                            new SetTikiTorchArm(tikiArmPos)
                        )
                    )
                )
            )
        );
    }

    public static Command rest() {
        return new SetEndeffector(ELEVATOR_REST_HEIGHT, TIKI_ALGAE_CLEAR, ALGAE_REST);
    }

    public static Command l1() {
        return new SetEndeffector(ELEVATOR_L1_HEIGHT, TIKI_L1_SCORE, ALGAE_REST);
    }

    public static Command l2() {
        return new SetEndeffector(ELEVATOR_L2_HEIGHT, TIKI_L234_SCORE, ALGAE_REST);
    }

    public static Command l3() {
        return new SetEndeffector(ELEVATOR_L3_HEIGHT, TIKI_L234_SCORE, ALGAE_REST);
    }

    public static Command l4() {
        return new SetEndeffector(ELEVATOR_L4_HEIGHT, TIKI_L234_SCORE, ALGAE_REST);
    }

    public static Command slowL4() {
        return new SetEndeffector(ELEVATOR_L4_HEIGHT, TIKI_L234_SCORE, ALGAE_REST, SLOW_MAX_ELEVATOR_SPEED);
    }

    public static Command mediumL4() {
        return new SetEndeffector(ELEVATOR_L4_HEIGHT, TIKI_AUTO_L234_SCORE, ALGAE_REST, MEDIUM_MAX_ELEVATOR_SPEED);
    }

    public static Command lowAlgae() {
        return new SetEndeffector(ELEVATOR_LOW_ALGAE_HEIGHT, TIKI_ALGAE_CLEAR, ALGAE_REEF_INTAKE);
    }

    public static Command highAlgae() {
        return new SetEndeffector(ELEVATOR_HIGH_ALGAE_HEIGHT, TIKI_ALGAE_CLEAR, ALGAE_REEF_INTAKE);
    }

    public static Command floor() {
        return new SetEndeffector(ELEVATOR_FLOOR_HEIGHT, TIKI_ALGAE_CLEAR, ALGAE_FLOOR_INTAKE);
    }

    public static Command barge() {
        return new SetEndeffector(ELEVATOR_BARGE_HEIGHT, TIKI_ALGAE_CLEAR, ALGAE_BARGE_SCORE);
    }

    public static Command processor() {
        return new SetEndeffector(ELEVATOR_FLOOR_HEIGHT, TIKI_ALGAE_CLEAR, ALGAE_FLOOR_INTAKE);
    }

    public static Command intake() {
        // return new SetEndeffector(ELEVATOR_INTAKE_HEIGHT, TIKI_INTAKE, ALGAE_REST);
        return new ParallelCommandGroup(
            Elevate.intake(),
            SetAlgaeArm.rest(),
            SetTikiTorchArm.intake()
        );
    }

    public static Command fastIntake() {
        // return new SetEndeffector(ELEVATOR_INTAKE_HEIGHT, TIKI_INTAKE, ALGAE_REST);
        return new ParallelCommandGroup(
            Elevate.intake(),
            SetAlgaeArm.rest(),
            SetTikiTorchArm.intake()
        );
    }

    public static Command autoIntake() {
        // return new SetEndeffector(ELEVATOR_AUTO_INTAKE_HEIGHT, TIKI_INTAKE, ALGAE_REST);
        return new ParallelCommandGroup(
            Elevate.autoIntake(),
            SetAlgaeArm.rest(),
            SetTikiTorchArm.intake()
        );
    }
}