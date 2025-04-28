package org.mort11.commands.actions.endeff.pid;

import org.mort11.config.constants.PhysicalConstants;
import org.mort11.subsystems.TikiTorchArm;
import org.mort11.subsystems.Elevator;

import static org.mort11.config.constants.PIDConstants.Elevator.MEDIUM_MAX_ELEVATOR_SPEED;
import static org.mort11.config.constants.PIDConstants.Elevator.POS_TELEOP_CONSTRAINTS;
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

    public SetEndeffector(double elevatorPos, double tikiArmPos, double algaeArmPos, double elevatorSpeed, double elevatorAcceleration) {

        addCommands(
            new SequentialCommandGroup(
                new ParallelCommandGroup(
                    SetTikiTorchArm.algaeClear(),
                    SetAlgaeArm.rest()
                ).withTimeout(0.5),

                new ParallelCommandGroup(
                    new Elevate(elevatorPos, elevatorSpeed, elevatorAcceleration),
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

    public static Command start() {
        return new SequentialCommandGroup(
            new ParallelCommandGroup(
                new Elevate(2),
                SetAlgaeArm.rest(),
                new SetTikiTorchArm(-45)
            ).withTimeout(0.4),
            Elevate.zero()
        );
    }

    public static Command l1() {
        // return new SetEndeffector(ELEVATOR_L1_HEIGHT, TIKI_L1_SCORE, ALGAE_REST);
        return new ParallelCommandGroup(
            Elevate.l1(),
            SetAlgaeArm.rest(),
            SetTikiTorchArm.l1()
        );
    }

    public static Command l2() {
        return new SetEndeffector(ELEVATOR_L2_HEIGHT, TIKI_L2_SCORE, ALGAE_REST);
    }

    public static Command l3() {
        return new SetEndeffector(ELEVATOR_L3_HEIGHT, TIKI_L3_SCORE, ALGAE_REST);
    }

    public static Command l4() {
        return new SetEndeffector(ELEVATOR_L4_HEIGHT, TIKI_L4_SCORE, ALGAE_REST);
    }

    public static Command teleopL4() {
        return new SetEndeffector(ELEVATOR_L4_HEIGHT, TIKI_L4_SCORE, ALGAE_REST, POS_TELEOP_CONSTRAINTS.maxVelocity, POS_TELEOP_CONSTRAINTS.maxAcceleration);
    }

    public static Command autoL4() {
        return new SetEndeffector(ELEVATOR_AUTO_L4_HEIGHT, TIKI_L4_SCORE, ALGAE_REST);
    }

    public static Command slowL4() {
        return new SetEndeffector(ELEVATOR_L4_HEIGHT, TIKI_L4_SCORE, ALGAE_REST, SLOW_MAX_ELEVATOR_SPEED);
    }

    public static Command mediumL4() {
        return new SetEndeffector(ELEVATOR_L4_HEIGHT, TIKI_L4_SCORE, ALGAE_REST, MEDIUM_MAX_ELEVATOR_SPEED);
    }

    public static Command lowAlgae() {
        return new SetEndeffector(ELEVATOR_LOW_ALGAE_HEIGHT, TIKI_ALGAE_CLEAR, ALGAE_REEF_INTAKE);
    }

    public static Command lowAutoAlgae() {
        return new SetEndeffector(ELEVATOR_LOW_ALGAE_HEIGHT, TIKI_ALGAE_CLEAR, ALGAE_AUTO_REEF_INTAKE);
    }

    public static Command highAlgae() {
        return new SetEndeffector(ELEVATOR_HIGH_ALGAE_HEIGHT, TIKI_ALGAE_CLEAR, ALGAE_REEF_INTAKE);
    }

    public static Command floor() {
        // return new SetEndeffector(ELEVATOR_FLOOR_HEIGHT, TIKI_ALGAE_CLEAR, ALGAE_FLOOR_INTAKE);
        return new ParallelCommandGroup(
            Elevate.floor(),
            SetAlgaeArm.floor(),
            SetTikiTorchArm.algaeClear()
        );
    }

    public static Command pop() {
        // return new SetEndeffector(ELEVATOR_POP_HEIGHT, TIKI_ALGAE_CLEAR, ALGAE_POP_INTAKE);
        return new ParallelCommandGroup(
            Elevate.pop(),
            SetAlgaeArm.pop(),
            SetTikiTorchArm.algaeClear()
        );
    }

    public static Command barge() {
        return new SetEndeffector(ELEVATOR_BARGE_HEIGHT, TIKI_ALGAE_CLEAR, ALGAE_BARGE_SCORE);
    }

    public static Command slowBarge() {
        return new SetEndeffector(ELEVATOR_BARGE_HEIGHT, TIKI_ALGAE_CLEAR, ALGAE_BARGE_SCORE, 40);
    }

    public static Command processor() {
        return new SetEndeffector(ELEVATOR_FLOOR_HEIGHT, TIKI_ALGAE_CLEAR, ALGAE_FLOOR_INTAKE);
    }

    public static Command maxScore() {
        return new SetEndeffector(-ELEVATOR_UPPER_LIMIT_SWITCH_HEIGHT, TIKI_ALGAE_CLEAR, ALGAE_REST);
    }

    public static Command intake() {
        // return new SetEndeffector(ELEVATOR_INTAKE_HEIGHT, TIKI_INTAKE, ALGAE_REST);
        return new SequentialCommandGroup(
            new ParallelCommandGroup(
                Elevate.zero(),
                SetAlgaeArm.rest(),
                SetTikiTorchArm.intake()
            ).withTimeout(1),
            new ParallelCommandGroup(
                Elevate.intake(),
                SetAlgaeArm.rest(),
                SetTikiTorchArm.intake()
            )
        );
        // new ParallelCommandGroup(
        //     Elevate.rest(),
        //     SetAlgaeArm.rest(),
        //     SetTikiTorchArm.intake()
        // ).withTimeout(0.75);
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
        // return new ParallelCommandGroup(
        //     Elevate.autoIntake(),
        //     SetAlgaeArm.rest(),
        //     SetTikiTorchArm.intake()
        // );

        return new SequentialCommandGroup(
            new ParallelCommandGroup(
                Elevate.zero(),
                SetAlgaeArm.rest(),
                SetTikiTorchArm.intake()
            ).withTimeout(1.25),
            new ParallelCommandGroup(
                Elevate.autoIntake(),
                SetAlgaeArm.rest(),
                SetTikiTorchArm.intake()
            )
        );
    }
}