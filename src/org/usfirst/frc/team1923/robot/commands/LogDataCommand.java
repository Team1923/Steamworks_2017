package org.usfirst.frc.team1923.robot.commands;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class LogDataCommand extends Command {

    private String message;

    /**
     * This command will continuously log data to a file on the rio until it is
     * interrupted
     */
    public LogDataCommand() {
        requires(Robot.debugSubSys);
    }

    /**
     * This command will have a single shot logging event
     *
     * @param message
     */
    public LogDataCommand(String message) {
        requires(Robot.debugSubSys);

        this.message = message;
    }

    // Called once when the command executes
    @Override
    protected void initialize() {
        if (this.message != null) {
            Robot.debugSubSys.logData(this.message);
        }
    }

    @Override
    protected void execute() {
        Robot.debugSubSys.logData();
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {

    }

    @Override
    protected boolean isFinished() {
        return this.message != null;
    }

}
