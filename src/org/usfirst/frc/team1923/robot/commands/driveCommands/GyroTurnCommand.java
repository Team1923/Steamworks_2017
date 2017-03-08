package org.usfirst.frc.team1923.robot.commands.driveCommands;

import org.usfirst.frc.team1923.robot.Robot;

import com.ctre.PigeonImu;
import com.ctre.CANTalon.TalonControlMode;
import com.ctre.PigeonImu.FusionStatus;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;

public class GyroTurnCommand extends Command {
	
	//TODO: Configure PID constants
	private final double P_CONST = 0.05;
	private final double I_CONST = 0;
	private final double D_CONST = 0;
	
	//Absolute turning tolerance
	private final double TOLERANCE = 5;
	
	private ImuTarget target;
	private Drivetrain output;
	private PIDController controller;
	
	private double degrees;

    public GyroTurnCommand(double degrees) {
        requires(Robot.driveSubSys);
        
        //Ensure that the degrees is [-360, 360] for the PID controller
        this.degrees = degrees % 360;
        this.target = new ImuTarget(this.degrees);
    	this.output = new Drivetrain();
    	
    	this.controller = new PIDController(
    			P_CONST, I_CONST, D_CONST,
    			this.target,
    			this.output
    	);
    	
    	this.controller.setContinuous(true);
    	this.controller.setAbsoluteTolerance(TOLERANCE);
    	this.controller.setOutputRange(-1, 1);
    	this.controller.setInputRange(-360, 360);
    	this.controller.setSetpoint(this.degrees);
    }

    protected void initialize() {
    	this.controller.enable();
    }

    protected void execute() {
    	//Empty body
    }

    protected boolean isFinished() {
        return this.controller.onTarget();
    }

    protected void end() {
    	this.controller.disable();
    	
    	this.controller = null;
    	this.target = null;
    	this.output = null;
    }

    protected void interrupted() {
    	this.end();
    }
    
    public class ImuTarget implements PIDSource {

    	private PigeonImu imu;
    	private FusionStatus fusionStatus;
    	private double degrees;
    	
    	public ImuTarget(double degrees) {
    		this.imu = Robot.driveSubSys.getImu();
    		this.fusionStatus = new FusionStatus();
    		this.degrees = degrees;
    		
    		this.imu.SetFusedHeading(0);
    	}

    	@Override
    	public void setPIDSourceType(PIDSourceType pidSource) {
    		
    	}

    	@Override
    	public PIDSourceType getPIDSourceType() {
    		return PIDSourceType.kDisplacement;
    	}

    	@Override
    	public double pidGet() {
    		return this.degrees - this.imu.GetFusedHeading(this.fusionStatus);
    	}
    	
    }
    
    public class Drivetrain implements PIDOutput {

		@Override
		public void pidWrite(double output) {
			Robot.driveSubSys.drive(output, -output, TalonControlMode.PercentVbus);
		}
    	
    }
    
}
