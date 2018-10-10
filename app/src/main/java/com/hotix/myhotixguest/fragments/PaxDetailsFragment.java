package com.hotix.myhotixguest.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.hotix.myhotixguest.R;
import com.hotix.myhotixguest.adapters.CiviliteSpinnerAdapter;
import com.hotix.myhotixguest.adapters.CountrySpinnerAdapter;
import com.hotix.myhotixguest.adapters.DocTypeSpinnerAdapter;
import com.hotix.myhotixguest.models.Pax;
import com.hotix.myhotixguest.models.ResponseMsg;
import com.hotix.myhotixguest.retrofit2.RetrofitClient;
import com.hotix.myhotixguest.retrofit2.RetrofitInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hotix.myhotixguest.helpers.Settings.GLOBAL_PAX_LIST;
import static com.hotix.myhotixguest.helpers.Settings.GLOBAL_START_DATA;
import static com.hotix.myhotixguest.helpers.Utils.dateFormater;
import static com.hotix.myhotixguest.helpers.Utils.showSnackbar;
import static com.hotix.myhotixguest.helpers.Utils.stringEmptyOrNull;

public class PaxDetailsFragment extends Fragment {

    private int mPax;

    private AppCompatSpinner civiliteSp;
    private AppCompatSpinner countriesSp;
    private AppCompatSpinner docTypeSp;

    private RelativeLayout civiliteViw;
    private RelativeLayout countriesView;
    private RelativeLayout docTypeView;

    private AppCompatCheckBox smookerCBox;
    private AppCompatCheckBox disabilityCBox;

    private RadioGroup sexeRG;
    private RadioGroup situationRG;

    private AppCompatRadioButton maleRB;
    private AppCompatRadioButton femaleRB;
    private AppCompatRadioButton singleRB;
    private AppCompatRadioButton marriedRB;
    private AppCompatRadioButton divorcedRB;

    private TextInputLayout first_name_il;
    private AppCompatEditText first_name_et;
    private TextInputLayout last_name_il;
    private AppCompatEditText last_name_et;
    private TextInputLayout bd_il;
    private AppCompatEditText bd_et;
    private TextInputLayout bp_il;
    private AppCompatEditText bp_et;
    private TextInputLayout adr_il;
    private AppCompatEditText adr_et;
    private TextInputLayout tel_il;
    private AppCompatEditText tel_et;
    private TextInputLayout mail_il;
    private AppCompatEditText mail_et;
    private TextInputLayout doc_id_il;
    private AppCompatEditText doc_id_et;
    private TextInputLayout job_il;
    private AppCompatEditText job_et;

    private AppCompatButton save_btn;

    private Pax paxData = new Pax();


    public PaxDetailsFragment() {
        // Required empty public constructor
    }

    public static PaxDetailsFragment newInstance(int param1) {
        PaxDetailsFragment fragment = new PaxDetailsFragment();
        Bundle args = new Bundle();
        args.putInt("mPax", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPax = getArguments().getInt("mPax");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pax_details, container, false);
        civiliteSp = (AppCompatSpinner) view.findViewById(R.id.pax_detail_frag_civilite_sp);
        countriesSp = (AppCompatSpinner) view.findViewById(R.id.pax_detail_frag_countries_sp);
        docTypeSp = (AppCompatSpinner) view.findViewById(R.id.pax_detail_frag_doc_type_sp);

        civiliteViw = (RelativeLayout) view.findViewById(R.id.pax_detail_frag_civilite_view);
        countriesView = (RelativeLayout) view.findViewById(R.id.pax_detail_frag_countries_view);
        docTypeView = (RelativeLayout) view.findViewById(R.id.pax_detail_frag_doc_type_view);

        smookerCBox = (AppCompatCheckBox) view.findViewById(R.id.pax_detail_smooker_chb);
        disabilityCBox = (AppCompatCheckBox) view.findViewById(R.id.pax_detail_disability_chb);

        sexeRG = (RadioGroup) view.findViewById(R.id.pax_detail_sexe_rGroup);
        situationRG = (RadioGroup) view.findViewById(R.id.pax_detail_situation_rGroup);

        maleRB = (AppCompatRadioButton) view.findViewById(R.id.pax_detail_sexe_m_rb);
        femaleRB = (AppCompatRadioButton) view.findViewById(R.id.pax_detail_sexe_f_rb);
        singleRB = (AppCompatRadioButton) view.findViewById(R.id.pax_detail_situation_single_rb);
        marriedRB = (AppCompatRadioButton) view.findViewById(R.id.pax_detail_situation_married_rb);
        divorcedRB = (AppCompatRadioButton) view.findViewById(R.id.pax_detail_situation_divorced_rb);

        first_name_il = (TextInputLayout) view.findViewById(R.id.pax_detail_first_name_il);
        first_name_et = (AppCompatEditText) view.findViewById(R.id.pax_detail_first_name_et);

        last_name_il = (TextInputLayout) view.findViewById(R.id.pax_detail_last_name_il);
        last_name_et = (AppCompatEditText) view.findViewById(R.id.pax_detail_last_name_et);

        bd_il = (TextInputLayout) view.findViewById(R.id.pax_detail_birth_date_il);
        bd_et = (AppCompatEditText) view.findViewById(R.id.pax_detail_birth_date_et);

        bp_il = (TextInputLayout) view.findViewById(R.id.pax_detail_birth_place_il);
        bp_et = (AppCompatEditText) view.findViewById(R.id.pax_detail_birth_Place_et);

        adr_il = (TextInputLayout) view.findViewById(R.id.pax_detail_address_il);
        adr_et = (AppCompatEditText) view.findViewById(R.id.pax_detail_address_et);

        tel_il = (TextInputLayout) view.findViewById(R.id.pax_detail_phone_il);
        tel_et = (AppCompatEditText) view.findViewById(R.id.pax_detail_phone_et);

        mail_il = (TextInputLayout) view.findViewById(R.id.pax_detail_mail_il);
        mail_et = (AppCompatEditText) view.findViewById(R.id.pax_detail_mail_et);

        doc_id_il = (TextInputLayout) view.findViewById(R.id.pax_detail_doc_id_il);
        doc_id_et = (AppCompatEditText) view.findViewById(R.id.pax_detail_doc_id_et);

        job_il = (TextInputLayout) view.findViewById(R.id.pax_detail_job_il);
        job_et = (AppCompatEditText) view.findViewById(R.id.pax_detail_job_et);

        save_btn = (AppCompatButton) view.findViewById(R.id.pax_detail_save_btn);

        initData();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        bd_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePax();
            }
        });
    }

    public void initData() {

        // loade spinner civilite
        if (GLOBAL_START_DATA.getCivilites() != null) {
            civiliteViw.setVisibility(View.VISIBLE);
            int cv = Integer.valueOf(GLOBAL_PAX_LIST.get(mPax).getCivilite());
            CiviliteSpinnerAdapter civiliteSpinnerAdapter = new CiviliteSpinnerAdapter(getActivity(), GLOBAL_START_DATA.getCivilites());
            civiliteSp.setAdapter(civiliteSpinnerAdapter);
            for (int i = 0; i < GLOBAL_START_DATA.getCivilites().size(); i++) {
                if (GLOBAL_START_DATA.getCivilites().get(i).getId() == cv)
                    civiliteSp.setSelection(i);
            }
        }

        // loade spinner countries
        if (GLOBAL_START_DATA.getPays() != null) {
            countriesView.setVisibility(View.VISIBLE);
            int pay = GLOBAL_PAX_LIST.get(mPax).getPaysId();
            CountrySpinnerAdapter countrySpinnerAdapter = new CountrySpinnerAdapter(getActivity(), GLOBAL_START_DATA.getPays());
            countriesSp.setAdapter(countrySpinnerAdapter);
            for (int i = 0; i < GLOBAL_START_DATA.getPays().size(); i++) {
                if (GLOBAL_START_DATA.getPays().get(i).getId() == pay) countriesSp.setSelection(i);
            }
        }

        // loade spinner doctype
        if (GLOBAL_START_DATA.getPieces() != null) {
            docTypeView.setVisibility(View.VISIBLE);
            int piec = GLOBAL_PAX_LIST.get(mPax).getPieceId();
            DocTypeSpinnerAdapter docTypeSpinnerAdapter = new DocTypeSpinnerAdapter(getActivity(), GLOBAL_START_DATA.getPieces());
            docTypeSp.setAdapter(docTypeSpinnerAdapter);
            for (int i = 0; i < GLOBAL_START_DATA.getPieces().size(); i++) {
                if (GLOBAL_START_DATA.getPieces().get(i).getId() == piec) docTypeSp.setSelection(i);
            }
        }

        //RadioGroup Sexe
        maleRB.setChecked(GLOBAL_PAX_LIST.get(mPax).getSexe().equals("M"));
        femaleRB.setChecked(GLOBAL_PAX_LIST.get(mPax).getSexe().equals("F"));

        //RadioGroup Situation
        singleRB.setChecked(GLOBAL_PAX_LIST.get(mPax).getSituation().equals("C"));
        marriedRB.setChecked(GLOBAL_PAX_LIST.get(mPax).getSituation().equals("M"));
        divorcedRB.setChecked(GLOBAL_PAX_LIST.get(mPax).getSituation().equals("D"));

        //CheckBox
        smookerCBox.setChecked(GLOBAL_PAX_LIST.get(mPax).getFumeur() == 1);
        disabilityCBox.setChecked(GLOBAL_PAX_LIST.get(mPax).getHandicape() == 1);

        //loade EditTexts
        first_name_et.setText(GLOBAL_PAX_LIST.get(mPax).getPrenom().trim());
        last_name_et.setText(GLOBAL_PAX_LIST.get(mPax).getNom().trim());
        bd_et.setText(dateFormater(GLOBAL_PAX_LIST.get(mPax).getDateNaissance(), "yyyy/MM/dd", "dd/MM/yyyy").trim());
        bp_et.setText(GLOBAL_PAX_LIST.get(mPax).getLieu().trim());
        adr_et.setText(GLOBAL_PAX_LIST.get(mPax).getAddresse().trim());
        if (!stringEmptyOrNull(GLOBAL_PAX_LIST.get(mPax).getGsm())) {
            tel_et.setText(GLOBAL_PAX_LIST.get(mPax).getGsm().trim());
        } else {
            tel_et.setText(GLOBAL_START_DATA.getPays().get(countriesSp.getSelectedItemPosition()).getCode().trim());
        }
        mail_et.setText(GLOBAL_PAX_LIST.get(mPax).getEmail().trim());
        doc_id_et.setText(GLOBAL_PAX_LIST.get(mPax).getDocNum().trim());
        job_et.setText(GLOBAL_PAX_LIST.get(mPax).getProfession().trim());
    }

    private void updatePax() {
        checkPaxData();
        RetrofitInterface service = RetrofitClient.getClient().create(RetrofitInterface.class);
        Call<ResponseMsg> userCall = service.updateReservationInfosQuery(
                paxData.getId().toString(),
                paxData.getNom(),
                paxData.getPrenom(),
                paxData.getPaysId().toString(),
                paxData.getAddresse(),
                paxData.getDateNaissance(),
                paxData.getLieu(),
                paxData.getSexe(),
                paxData.getSituation(),
                paxData.getFumeur().toString(),
                paxData.getHandicape().toString(),
                paxData.getPieceId().toString(),
                paxData.getDocNum(),
                paxData.getEmail(),
                paxData.getGsm(),
                paxData.getProfession(),
                "",
                paxData.getCivilite());

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(), R.style.AppThemeDialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Response...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        userCall.enqueue(new Callback<ResponseMsg>() {
            @Override
            public void onResponse(Call<ResponseMsg> call, Response<ResponseMsg> response) {

                progressDialog.dismiss();

                if (response.raw().code() == 200) {
                    ResponseMsg msg = response.body();
                    if (msg.getIsOk()) {
                        showSnackbar(getActivity().findViewById(android.R.id.content), getString(R.string.update_success));
                    } else {
                        showSnackbar(getActivity().findViewById(android.R.id.content), getString(R.string.something_wrong));
                    }
                } else {
                    showSnackbar(getActivity().findViewById(android.R.id.content), response.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseMsg> call, Throwable t) {
                progressDialog.dismiss();
                showSnackbar(getActivity().findViewById(android.R.id.content), getString(R.string.server_down));
            }
        });

    }

    private void checkPaxData() {

        paxData.setId(GLOBAL_PAX_LIST.get(mPax).getId());

        if (GLOBAL_START_DATA.getCivilites() != null) {
            paxData.setCivilite(GLOBAL_START_DATA.getCivilites().get(civiliteSp.getSelectedItemPosition()).getId().toString());
        } else {
            paxData.setCivilite(GLOBAL_PAX_LIST.get(mPax).getCivilite());
        }

        if (GLOBAL_START_DATA.getPays() != null) {
            paxData.setPaysId(GLOBAL_START_DATA.getPays().get(countriesSp.getSelectedItemPosition()).getId());
        } else {
            paxData.setPaysId(GLOBAL_PAX_LIST.get(mPax).getPaysId());
        }

        if (GLOBAL_START_DATA.getPieces() != null) {
            paxData.setPieceId(GLOBAL_START_DATA.getPieces().get(docTypeSp.getSelectedItemPosition()).getId());
        } else {
            paxData.setPieceId(GLOBAL_PAX_LIST.get(mPax).getPieceId());
        }

        paxData.setPrenom(first_name_et.getText().toString());
        paxData.setNom(last_name_et.getText().toString());
        paxData.setGsm(tel_et.getText().toString());
        paxData.setEmail(mail_et.getText().toString());
        paxData.setDateNaissance(dateFormater(bd_et.getText().toString(), "dd/MM/yyyy", "yyyyMMdd"));
        paxData.setLieu(bp_et.getText().toString());
        paxData.setAddresse(adr_et.getText().toString());
        paxData.setDocNum(doc_id_et.getText().toString());
        paxData.setProfession(job_et.getText().toString());

        if (smookerCBox.isChecked()) {
            paxData.setFumeur(1);
        } else {
            paxData.setFumeur(0);
        }

        if (disabilityCBox.isChecked()) {
            paxData.setHandicape(1);
        } else {
            paxData.setHandicape(0);
        }

        if (maleRB.isChecked()) {
            paxData.setSexe("M");
        } else {
            paxData.setSexe("F");
        }


        if (singleRB.isChecked()) {
            paxData.setSituation("C");
        } else if (marriedRB.isChecked()) {
            paxData.setSituation("M");
        } else {
            paxData.setSituation("D");
        }

    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new PaxUpdateDatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

}
