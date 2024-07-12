package live.saugat.stocklive.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import live.saugat.stocklive.R;
import org.jetbrains.annotations.NotNull;

public class FilterBottomSheet extends BottomSheetDialogFragment {
    private FilterListener listener;

    public interface FilterListener{
        void onFilterApplied(String filter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.filter_bottom_sheet, container, false);
        EditText filterEditText = view.findViewById(R.id.filterEditText);
        Button applyButton = view.findViewById(R.id.applyButton);
        applyButton.setOnClickListener(v -> {
            String filter = filterEditText.getText().toString();
            if (listener != null) {
                listener.onFilterApplied(filter);
            }
            dismiss();
        });
        return view;
    }

    public void setListener(FilterListener listener){
        this.listener = listener;
    }
}
