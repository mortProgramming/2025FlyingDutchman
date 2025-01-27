package org.mort11.commands;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.List;

public class AutoGenerator extends SequentialCommandGroup {
    SequentialCommandGroup auto;
    int pathIndex;

    public AutoGenerator() {
        auto = new SequentialCommandGroup();
    }

    // public Command generate(PathPlannerAuto autoName, Command... otherCommand) {
    //     auto = new SequentialCommandGroup();

    //     // List <PathPlannerPath> paths = PathPlannerAuto.getPathGroupFromAutoFile(autoName);

    //     int greaterLength = (otherCommand.length > paths.size() ? otherCommand.length : paths.size());

    //     for (int i = 0; i < greaterLength; i++){
        
    //         if(otherCommand.length < i && paths.size() >= i) {
    //             auto = new SequentialCommandGroup(
    //                 auto,
    //                 AutoBuilder.followPath(paths.get(i))
    //             );
    //         }

    //         else if(otherCommand.length >= i && paths.size() < i) {
    //             auto = new SequentialCommandGroup(
    //                 auto, 
    //                 otherCommand[i]
    //             );
    //         }

    //         else {
    //             auto = new SequentialCommandGroup(
    //                 auto,
    //                 otherCommand[i], 
    //                 AutoBuilder.followPath(paths.get(i))
    //             );
    //         }
    //     }

    //     return auto;
    // }

    public Command generate(List<PathPlannerPath> paths, Command... otherCommand) {
        auto = new SequentialCommandGroup();

        int greaterLength = (otherCommand.length > paths.size() ? otherCommand.length : paths.size());

        for (int i = 0; i < greaterLength; i++){
        
            if(otherCommand.length < i && paths.size() >= i) {
                auto = new SequentialCommandGroup(
                    auto,
                    AutoBuilder.followPath(paths.get(i))
                );
            }

            else if(otherCommand.length >= i && paths.size() < i) {
                auto = new SequentialCommandGroup(
                    auto, 
                    otherCommand[i]
                );
            }

            else {
                auto = new SequentialCommandGroup(
                    auto,
                    otherCommand[i], 
                    AutoBuilder.followPath(paths.get(i))
                );
            }
        }

        return auto;
    }
}
