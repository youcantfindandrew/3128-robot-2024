package frc.team3128.subsystems;

import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.CANdle.LEDStripType;
import com.ctre.phoenix.led.CANdleConfiguration;
import com.ctre.phoenix.led.FireAnimation;
import com.ctre.phoenix.led.RainbowAnimation;
import com.ctre.phoenix.led.SingleFadeAnimation;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team3128.Robot;
import frc.team3128.Constants.LedConstants;
import frc.team3128.Constants.LedConstants.Colors;;


public class Leds extends SubsystemBase {
    private final CANdle m_candle = new CANdle(LedConstants.CANDLE_ID);

    private static Leds instance;

    public static Leds getInstance() {
        if (instance == null) {
            instance = new Leds();
        }
        return instance;
    }

    public Leds() {
        configCandle();
    }

    private void configCandle() {
        CANdleConfiguration config = new CANdleConfiguration();
        config.stripType = LEDStripType.RGB;
        config.statusLedOffWhenActive = true;
        config.brightnessScalar = 1;
        m_candle.configAllSettings(config);
    }

    public void setDefaultColor() {
        setLedColor(Colors.FLAME);
        //setLedColor(Robot.getAlliance() == Alliance.Red ? Colors.RED : Colors.BLUE);
    }

    //Set Elevator Leds
    public void setLedColor(Colors color) {

        switch (color) {
            case AMP:
                m_candle.animate(new RainbowAnimation(LedConstants.RainbowAnimation.BRIGHTNESS,LedConstants.RainbowAnimation.SPEED,LedConstants.PIVOT_COUNT_FRONT,false,LedConstants.STARTING_ID),0);
                m_candle.animate(new RainbowAnimation(LedConstants.RainbowAnimation.BRIGHTNESS,LedConstants.RainbowAnimation.SPEED,LedConstants.PIVOT_COUNT_BACK,true,LedConstants.STARTING_ID+LedConstants.PIVOT_COUNT_FRONT),1);
                break;
            case FLAME:
                m_candle.animate(new FireAnimation(LedConstants.RainbowAnimation.BRIGHTNESS,0.75,LedConstants.PIVOT_COUNT_FRONT - 5,1,0.3),0);
                m_candle.animate(new FireAnimation(LedConstants.RainbowAnimation.BRIGHTNESS,0.75,LedConstants.PIVOT_COUNT_FRONT - 10,1,0.3, true, 55 + 5),1);
                break;
            case PIECE:
            case ERROR:
                resetAnimationSlot(1,1);
                m_candle.animate(new SingleFadeAnimation(color.r, color.g, color.b,LedConstants.WHITE_VALUE,LedConstants.HOLDING_SPEED,LedConstants.PIVOT_COUNT),0);
                break;
            default:
                resetAnimationSlot(2);
                m_candle.setLEDs(color.r,color.g,color.b,LedConstants.WHITE_VALUE,LedConstants.STARTING_ID,LedConstants.PIVOT_COUNT);
                break;
        }
    }

    public void resetAnimationSlot(int slots) {
        for (int i = 0; i < slots; i++) {
         m_candle.animate(null, i);
        }
     }

    public void resetAnimationSlot(int slots, int offset) {
       for (int i = 0; i < slots; i++) {
        m_candle.animate(null, i+offset);
       }
    }

    
    

}