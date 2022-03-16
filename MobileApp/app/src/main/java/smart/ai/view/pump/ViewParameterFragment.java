package smart.ai.view.pump;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import smart.ai.R;
import smart.ai.model.ECDgree;
import smart.ai.model.PHDgree;
import smart.ai.model.Pressure;
import smart.ai.util.AppUtil;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewParameterFragment extends Fragment {

    private static final String TAG = ViewParameterFragment.class.getSimpleName();

    @BindView(R.id.tvDegreePH)
    TextView tvDegreePH;

    @BindView(R.id.tvDegreeEC)
    TextView tvDegreeEC;

    @BindView(R.id.tvPressure)
    TextView tvPressure;

    public MqttAndroidClient client;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_parameter, container, false);

        addControls(view);
        addEvents();

        return view;
    }

    private void addEvents() {
        connect();
    }

    private void addControls(View view) {
        ButterKnife.bind(this, view);
    }

    void connect() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(AppUtil.USER_MQTT);
        options.setPassword(AppUtil.PASS_MQTT.toCharArray());

        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(getContext(), AppUtil.SERVER_URI_MQTT, clientId);
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {

                Log.d(TAG, "messageArrived: " + message.toString());
                Log.d(TAG, "messageArrived: " + topic);
                if (topic != null){

                    if (topic.equalsIgnoreCase(AppUtil.TOPIC_FORMULA_DEGREE_PH)){
                        PHDgree ph = new PHDgree(UUID.randomUUID().toString(), Double.parseDouble(message.toString()));
                        tvDegreePH.setText(ph.toString());
                    }

                    if (topic.equalsIgnoreCase(AppUtil.TOPIC_FORMULA_DEGREE_EC)){
                        ECDgree ec = new ECDgree(UUID.randomUUID().toString(), Double.parseDouble(message.toString()));
                        tvDegreeEC.setText(ec.toString());
                    }

                    if (topic.equalsIgnoreCase(AppUtil.TOPIC_FORMULA_PRESSURE)){
                        Pressure pressure = new Pressure(UUID.randomUUID().toString(), Double.parseDouble(message.toString()));
                        tvPressure.setText(pressure.toString());
                    }
                }
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

        try {
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d(TAG, "onSuccess");

                    sub();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d(TAG, "onFailure");

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    void sub() {
        int qos = 1;

        String topic_degree_ph = AppUtil.TOPIC_FORMULA_DEGREE_PH;
        try {
            IMqttToken subTokenDegreePH = client.subscribe(topic_degree_ph, qos);
            subTokenDegreePH.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d(TAG, "onSuccess: subscribe");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken,
                                      Throwable exception) {
                    Log.d(TAG, "onFailure: subscribe fail");

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

        String topic_degree_ec = AppUtil.TOPIC_FORMULA_DEGREE_EC;
        try {
            IMqttToken subTokenDegreeEC = client.subscribe(topic_degree_ec, qos);
            subTokenDegreeEC.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d(TAG, "onSuccess: subscribe");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken,
                                      Throwable exception) {
                    Log.d(TAG, "onFailure: subscribe fail");

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

        String topic_pressure = AppUtil.TOPIC_FORMULA_PRESSURE;
        try {
            IMqttToken subTokenPressure = client.subscribe(topic_pressure, qos);
            subTokenPressure.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d(TAG, "onSuccess: subscribe");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken,
                                      Throwable exception) {
                    Log.d(TAG, "onFailure: subscribe fail");

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
