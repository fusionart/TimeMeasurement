package Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import Model.TimeMeasurementHeader;

public class TimeMeasurementHeaderDaoImpl implements Dao<TimeMeasurementHeader>{
	
	 private List<TimeMeasurementHeader> tmHeader = new ArrayList<>();

	@Override
	public Optional<TimeMeasurementHeader> get(long id) {
		return Optional.ofNullable(tmHeader.get((int) id));
	}

	@Override
	public List<TimeMeasurementHeader> getAll() {
		return tmHeader;
	}

	@Override
	public void save(TimeMeasurementHeader t) {
		tmHeader.add(t);
	}

	@Override
	public void update(TimeMeasurementHeader t, String[] params) {
		t.setName(Objects.requireNonNull(params[0], "Name cannot be null"));
		tmHeader.add(t);
	}

	@Override
	public void delete(TimeMeasurementHeader t) {
		tmHeader.remove(t);
	}
}
