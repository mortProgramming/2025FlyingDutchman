package org.mort11.commands.autons;

import java.util.List;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutoGenerator extends SequentialCommandGroup {
    static SequentialCommandGroup auto;
    static List<PathPlannerPath> paths;
    static int pathIndex;

    public AutoGenerator() {
        auto = new SequentialCommandGroup();
    }

    public static Command generate(String autoName, Command... otherCommand) throws ClassNotFoundException {
        auto = new SequentialCommandGroup();

        try {
            paths = PathPlannerAuto.getPathGroupFromAutoFile(autoName);
        }

        catch(Exception e) {
            throw new ClassNotFoundException("spell your auto right");
        }

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

    public static Command generate(List<PathPlannerPath> paths, Command... otherCommand) {
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
