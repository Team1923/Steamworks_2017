package org.usfirst.frc.team1923.robot.utils.debug;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LogDataCommand extends Command {

    private String message;

    /**
     * This command will continuously log data to a file on the rio until it is
     * interrupted
     */
    public LogDataCommand() {
        super();
        requires(Robot.debug);
    }

    /**
     * This command will have a single shot logging event
     * 
     * @param message
     */
    public LogDataCommand(String message) {
        super();
        requires(Robot.debug);
        this.message = message;
    }

    // Called once when the command executes
    @Override
    protected void initialize() {
        if (this.message != null) {
            Robot.debug.logEvent(this.message);
        }
    }

    @Override
    protected void execute() {
        Robot.debug.logData();
    }

    @Override
    protected void end() { // Do nothing and wait for the next log event. File
                           // closing is handled in Disabled Init
    }

    @Override
    protected void interrupted() {
    }

    @Override
    protected boolean isFinished() {
        return this.message != null;
    }

}
