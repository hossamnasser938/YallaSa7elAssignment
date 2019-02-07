package com.example.android.yallasa7elassignment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.yallasa7elassignment.data.Space;

import java.util.List;

public class SpaceAdapter extends ArrayAdapter<Space> {

    public SpaceAdapter(@NonNull Context context, @NonNull List<Space> spaces) {
        super(context, 0, spaces);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Inflate a new layout if not exists
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.space_layout, parent, false);
        }

        // Get current space to be viewed
        Space currentItem = getItem(position);

        // Get reference to space views
        TextView titleView = listItemView.findViewById(R.id.space_title);
        TextView destinationView = listItemView.findViewById(R.id.space_destination);
        TextView addressView = listItemView.findViewById(R.id.space_address);
        TextView roomsView = listItemView.findViewById(R.id.space_rooms);
        TextView bathroomsView = listItemView.findViewById(R.id.space_bathrooms);
        TextView basePriceView = listItemView.findViewById(R.id.space_baseprice);
        TextView ownerNameView = listItemView.findViewById(R.id.space_ownername);
        TextView ownerPhoneView = listItemView.findViewById(R.id.space_ownerphone);

        // Populate space views from current space object
        titleView.setText( currentItem.getTitle()  );
        destinationView.setText( getContext().getString(R.string.destination_) + currentItem.getDestination() );
        addressView.setText( getContext().getString(R.string.address_) + currentItem.getAddress() );
        roomsView.setText( getContext().getString(R.string.rooms_) + currentItem.getNumberOfRooms() );
        bathroomsView.setText( getContext().getString(R.string.bathrooms_) + currentItem.getNumberOfBathrooms() );
        basePriceView.setText( getContext().getString(R.string.baseprice_) + currentItem.getBasePrice() );
        ownerNameView.setText( getContext().getString(R.string.ownername_) + currentItem.getOwnerName() );
        ownerPhoneView.setText( getContext().getString(R.string.ownerphone_) + currentItem.getMobileNumber() );

        // Since address attribute in table Space can be null, handle that issue
        if ( currentItem.getAddress() == null ) {
            addressView.setVisibility(View.GONE);
        }

        return listItemView;
    }
}
