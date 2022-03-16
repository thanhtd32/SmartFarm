package smart.ai.view.pump;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import smart.ai.R;
import smart.ai.model.NutritionalFormula;
import smart.ai.model.StatusFormula;
import smart.ai.util.AppUtil;
import smart.ai.util.Format;
import smart.ai.util.MyDatabaseHelper;
import com.google.gson.Gson;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MixFormulaFragment extends Fragment {

    private static final String TAG = MixFormulaFragment.class.getSimpleName();

    @BindView(R.id.spFormula)
    Spinner spFormula;

    @BindView(R.id.tvWaterStatus)
    TextView tvWaterStatus;

    @BindView(R.id.tvLiquidOneStatus)
    TextView tvLiquidOneStatus;

    @BindView(R.id.tvLiquidTwoStatus)
    TextView tvLiquidTwoStatus;

    @BindView(R.id.tvLiquidThreeStatus)
    TextView tvLiquidThreeStatus;

    @BindView(R.id.tvLiquidFourStatus)
    TextView tvLiquidFourStatus;

    @BindView(R.id.edtQuantity)
    EditText edtQuantity;

    @BindView(R.id.tvWaterQuantity)
    TextView tvWaterQuantity;

    @BindView(R.id.tvLiquidOneQuantity)
    TextView tvLiquidOneQuantity;

    @BindView(R.id.tvLiquidTwoQuantity)
    TextView tvLiquidTwoQuantity;

    @BindView(R.id.tvLiquidThreeQuantity)
    TextView tvLiquidThreeQuantity;

    @BindView(R.id.tvLiquidFourQuantity)
    TextView tvLiquidFourQuantity;

    @BindView(R.id.imgPumpOne)
    ImageView imgPumpOne;

    @BindView(R.id.imgPumpTwo)
    ImageView imgPumpTwo;

    @BindView(R.id.imgPumpThree)
    ImageView imgPumpThree;

    @BindView(R.id.tvPressureOne)
    TextView tvPressureOne;

    @BindView(R.id.tvPressureTwo)
    TextView tvPressureTwo;

    @BindView(R.id.tvPressureThree)
    TextView tvPressureThree;

    @BindView(R.id.cardViewWater)
    CardView cardViewWater;

    @BindView(R.id.cardViewOne)
    CardView cardViewOne;

    @BindView(R.id.cardViewTwo)
    CardView cardViewTwo;

    @BindView(R.id.cardViewThree)
    CardView cardViewThree;

    @BindView(R.id.cardViewFour)
    CardView cardViewFour;

    private Boolean IsMixing = false;

    private List<NutritionalFormula> nutritionalFormulas;
    private ArrayAdapter<NutritionalFormula> nutritionalFormulaAdapter;
    private NutritionalFormula formulaSelected;

    public MqttAndroidClient client;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mix_formula, container, false);

        Log.d(TAG, "onCreateView: " + "Mix");
        addControls(view);
        addEvents();
        settingDatabase();

        return view;
    }

    public void settingDatabase() {
        MyDatabaseHelper db = new MyDatabaseHelper(getContext());
        db.createDefaultFormula();

        nutritionalFormulas.clear();
        nutritionalFormulas.addAll(db.getAllFormulas());
        nutritionalFormulaAdapter.notifyDataSetChanged();
//        if (nutritionalFormulas.size() > 0){
//            spFormula.setSelection(0);
//        }
    }

    private void addEvents() {
        spFormula.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected: " + position);
                if (position >= 0 && position <= nutritionalFormulas.size()) {
                    formulaSelected = nutritionalFormulas.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void addControls(View view) {
        ButterKnife.bind(this, view);
        setupSpinner();
        connect();
    }

    private void setupSpinner() {
        nutritionalFormulas = new ArrayList<>();
        nutritionalFormulaAdapter = new ArrayAdapter<NutritionalFormula>(getActivity(), android.R.layout.simple_spinner_item, nutritionalFormulas);
        nutritionalFormulaAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spFormula.setAdapter(nutritionalFormulaAdapter);
    }

    @OnClick(R.id.btStartMix)
    void onClickStartMix() {
        startMixFormula();
    }

    @OnClick(R.id.btCancelMix)
    void onClickCancelMix() {
        cancelMixFormula();
    }

    private void cancelMixFormula() {
        if (IsMixing) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(R.string.msg_choose_cancel_mix_formula);
            builder.setPositiveButton(R.string.title_yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    JSONObject formula = new JSONObject();
                    try {
                        formula.put("pump", 1);
                        formula.put("status", false);
                        formula.put("liquid", "a");
                        formula.put("value", 0);
                        formula.put("speed", 0);

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    pub(formula.toString(), AppUtil.TOPIC_FORMULA_PUMP_STATUS);
                    Toast.makeText(getContext(), R.string.msg_success, Toast.LENGTH_SHORT).show();

                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(R.string.title_no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            Toast.makeText(getContext(), R.string.msg_mix_formula_is_not_mixing, Toast.LENGTH_SHORT).show();
        }
    }

    private void startMixFormula() {
        if (IsMixing) {
            Toast.makeText(getContext(), R.string.msg_mix_formula_is_mixing, Toast.LENGTH_SHORT).show();
            return;
        }

        if (edtQuantity.getText() == null || edtQuantity.getText().toString().isEmpty() || formulaSelected == null) {
            Toast.makeText(getContext(), R.string.msg_please_select_formula_and_enter_quantity, Toast.LENGTH_SHORT).show();
        } else {

            IsMixing = true;
            double quantity = Double.parseDouble(edtQuantity.getText().toString());
            double waterQuantity = quantity * formulaSelected.getPercentWater() / 100;
            double liquidOneQuantity = quantity * formulaSelected.getPercentNutritionalOne() / 100;
            double liquidTwoQuantity = quantity * formulaSelected.getPercentNutritionalTwo() / 100;
            double liquidThreeQuantity = quantity * formulaSelected.getPercentNutritionalThree() / 100;
            double liquidFourQuantity = quantity * formulaSelected.getPercentNutritionalFour() / 100;

            tvWaterQuantity.setText(waterQuantity + "");
            tvLiquidOneQuantity.setText(liquidOneQuantity + "");
            tvLiquidTwoQuantity.setText(liquidTwoQuantity + "");
            tvLiquidThreeQuantity.setText(liquidThreeQuantity + "");
            tvLiquidFourQuantity.setText(liquidFourQuantity + "");

            StatusFormula statusFormula = new StatusFormula(1,
                    true,
                    formulaSelected.getId(),
                    "0",
                    0,
                    quantity,
                    waterQuantity,
                    liquidOneQuantity,
                    liquidTwoQuantity,
                    liquidThreeQuantity,
                    liquidFourQuantity
                    );

            Gson gson = new Gson();
            String formula = gson.toJson(statusFormula);

            pub(formula, AppUtil.TOPIC_FORMULA);
            Toast.makeText(getContext(), R.string.msg_success, Toast.LENGTH_SHORT).show();
        }
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
                Log.d(TAG, "messageArrived: " + topic);
                Log.d(TAG, "messageArrived: " + message.toString());

                if (topic != null) {

                    if (topic.equalsIgnoreCase(AppUtil.TOPIC_FORMULA)) {
                        JSONObject jsonFormula = new JSONObject(message.toString());

                        Gson gson = new Gson();
                        StatusFormula formula = gson.fromJson(String.valueOf(jsonFormula), StatusFormula.class);
                        Log.d(TAG, "messageArrived: " + formula);

                        if (formula.isStatus()){
                            edtQuantity.setText(formula.getQuantity() + "");
                            tvWaterQuantity.setText(formula.getValueWater() + "");
                            tvLiquidOneQuantity.setText(formula.getValueOne() + "");
                            tvLiquidTwoQuantity.setText(formula.getValueTwo() + "");
                            tvLiquidThreeQuantity.setText(formula.getValueThree() + "");
                            tvLiquidFourQuantity.setText(formula.getValueFour() + "");

                            edtQuantity.setEnabled(false);

                            int index = 0;
                            for (NutritionalFormula nutritionalFormula: nutritionalFormulas) {
                                if (nutritionalFormula.getId().equalsIgnoreCase(formula.getFormulaId())){
                                    formulaSelected = nutritionalFormula;
                                    spFormula.setSelection(index);
                                    spFormula.setEnabled(false);
                                    break;
                                }
                                index++;
                            }
                        }else {
                            edtQuantity.setText("0");
                            tvWaterQuantity.setText("0");
                            tvLiquidOneQuantity.setText("0");
                            tvLiquidTwoQuantity.setText("0");
                            tvLiquidThreeQuantity.setText("0");
                            tvLiquidFourQuantity.setText("0");

                            edtQuantity.setEnabled(true);
                            spFormula.setEnabled(true);
                        }
                    }

                    if (topic.equalsIgnoreCase(AppUtil.TOPIC_FORMULA_PUMP_STATUS)) {
                       // JSONObject json = new JSONObject(message.toString());

                        ColorStateList stateList = ColorStateList.valueOf(getContext().getResources().getColor(R.color.blackContentNormal));
                        imgPumpOne.setBackgroundTintList(stateList);
                        imgPumpTwo.setBackgroundTintList(stateList);
                        imgPumpThree.setBackgroundTintList(stateList);

                        tvPressureOne.setText(0 + " ml/s");
                        tvPressureTwo.setText(0 + " ml/s");
                        tvPressureThree.setText(0 + " ml/s");

                        ColorStateList stateListRun = ColorStateList.valueOf(getContext().getResources().getColor(R.color.purple_500));

                        ColorStateList stateViewCard = ColorStateList.valueOf(getContext().getResources().getColor(R.color.blackLight));
                        cardViewWater.setBackgroundTintList(stateViewCard);
                        cardViewOne.setBackgroundTintList(stateViewCard);
                        cardViewTwo.setBackgroundTintList(stateViewCard);
                        cardViewThree.setBackgroundTintList(stateViewCard);
                        cardViewFour.setBackgroundTintList(stateViewCard);

                        ColorStateList stateCardViewCurrent = ColorStateList.valueOf(getContext().getResources().getColor(R.color.blackContentNormal));

                        Gson gson = new Gson();
                        StatusFormula statusFormula = gson.fromJson(String.valueOf(message.toString()), StatusFormula.class);
                        Log.d(TAG, "messageArrived: " + statusFormula);

                        if (statusFormula.isStatus()) {
                            IsMixing = true;

                            double waterQuantity = Double.parseDouble(tvWaterQuantity.getText().toString());
                            double percentWater = statusFormula.getValueWater() / waterQuantity * 100;
                            tvWaterStatus.setText(Format.precision_1_decimal.format( percentWater > 100 ? 100 : percentWater) + "%");

                            double liquidOne = Double.parseDouble(tvLiquidOneQuantity.getText().toString());
                            double percentLiquidOne = statusFormula.getValueOne() / liquidOne * 100;
                            tvLiquidOneStatus.setText(Format.precision_1_decimal.format(percentLiquidOne > 100 ? 100 : percentLiquidOne) + "%");

                            double liquidTwo = Double.parseDouble(tvLiquidTwoQuantity.getText().toString());
                            double percentLiquidTwo = statusFormula.getValueTwo() / liquidTwo * 100;
                            tvLiquidTwoStatus.setText(Format.precision_1_decimal.format(percentLiquidTwo > 100 ? 100 : percentLiquidTwo) + "%");

                            double liquidThree = Double.parseDouble(tvLiquidThreeQuantity.getText().toString());
                            double percentLiquidThree = statusFormula.getValueThree() / liquidThree * 100;
                            tvLiquidThreeStatus.setText(Format.precision_1_decimal.format(percentLiquidThree > 100 ? 100 : percentLiquidThree) + "%");

                            double liquidFour = Double.parseDouble(tvLiquidFourQuantity.getText().toString());
                            double percentLiquidFour = statusFormula.getValueFour() / liquidFour * 100;
                            tvLiquidFourStatus.setText(Format.precision_1_decimal.format(percentLiquidFour > 100 ? 100 : percentLiquidFour) + "%");

                            double speed = statusFormula.getSpeed();
                            switch (statusFormula.getLiquid()) {
                                case "a":
                                    cardViewWater.setBackgroundTintList(stateCardViewCurrent);
                                    imgPumpOne.setBackgroundTintList(stateListRun);
                                    tvPressureOne.setText(Format.precision_1_decimal.format(speed) + " ml/s");
                                    break;
                                case "b":
                                    cardViewOne.setBackgroundTintList(stateCardViewCurrent);
                                    imgPumpTwo.setBackgroundTintList(stateListRun);
                                    tvPressureTwo.setText(Format.precision_1_decimal.format(speed) + " ml/s");
                                    break;
                                case "c":
                                    cardViewTwo.setBackgroundTintList(stateCardViewCurrent);
                                    imgPumpTwo.setBackgroundTintList(stateListRun);
                                    tvPressureTwo.setText(Format.precision_1_decimal.format(speed) + " ml/s");
                                    break;
                                case "d":
                                    cardViewThree.setBackgroundTintList(stateCardViewCurrent);
                                    imgPumpThree.setBackgroundTintList(stateListRun);
                                    tvPressureThree.setText(Format.precision_1_decimal.format(speed) + " ml/s");
                                    break;
                                case "e":
                                    cardViewFour.setBackgroundTintList(stateCardViewCurrent);
                                    imgPumpThree.setBackgroundTintList(stateListRun);
                                    tvPressureThree.setText(Format.precision_1_decimal.format(speed) + " ml/s");
                                    break;
                                default:
                                    break;
                            }
                        } else {

                            IsMixing = false;

                            tvWaterStatus.setText("0 %");
                            tvLiquidOneStatus.setText("0 %");
                            tvLiquidTwoStatus.setText("0 %");
                            tvLiquidThreeStatus.setText("0 %");
                            tvLiquidFourStatus.setText("0 %");

                            tvWaterQuantity.setText("0");
                            tvLiquidOneQuantity.setText("0");
                            tvLiquidTwoQuantity.setText("0");
                            tvLiquidThreeQuantity.setText("0");
                            tvLiquidFourQuantity.setText("0");

                            edtQuantity.setText("");
                            edtQuantity.setEnabled(true);
                            spFormula.setEnabled(true);
                        }
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

    void pub(String content, String topic) {
        String payload = content;
        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = payload.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            message.setRetained(true);
            client.publish(topic, message);
        } catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
        }
    }

    void sub() {
        String topic_formula = AppUtil.TOPIC_FORMULA;
        int qos = 1;
        try {
            IMqttToken subToken = client.subscribe(topic_formula, qos);
            subToken.setActionCallback(new IMqttActionListener() {
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

        String topic = AppUtil.TOPIC_FORMULA_PUMP_STATUS;
        try {
            IMqttToken subToken = client.subscribe(topic, qos);
            subToken.setActionCallback(new IMqttActionListener() {
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
