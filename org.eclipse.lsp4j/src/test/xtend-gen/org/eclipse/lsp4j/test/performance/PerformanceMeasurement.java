package org.eclipse.lsp4j.test.performance;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.SymbolKind;
import org.eclipse.lsp4j.jsonrpc.json.JsonRpcMethod;
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class PerformanceMeasurement implements Runnable {
  private interface Measurement extends Runnable {
    public abstract void prepare();
  }
  
  public static void main(final String[] args) {
    new PerformanceMeasurement().run();
  }
  
  private final Random random = new Random(0);
  
  @Override
  public void run() {
    this.measureSymbolInformation();
  }
  
  public void measureSymbolInformation() {
    abstract class __PerformanceMeasurement_1 implements PerformanceMeasurement.Measurement {
      static final int SIZE = 100000;
      
      Gson gson;
      
      List<SymbolInformation> data;
    }
    
    __PerformanceMeasurement_1 ___PerformanceMeasurement_1 = new __PerformanceMeasurement_1() {
      @Override
      public void prepare() {
        ArrayList<SymbolInformation> _arrayList = new ArrayList<SymbolInformation>(__PerformanceMeasurement_1.SIZE);
        this.data = _arrayList;
        for (int i = 0; (i < __PerformanceMeasurement_1.SIZE); i++) {
          SymbolInformation _symbolInformation = new SymbolInformation();
          final Procedure1<SymbolInformation> _function = (SymbolInformation it) -> {
            it.setName(PerformanceMeasurement.this.randomString());
            int _nextInt = PerformanceMeasurement.this.random.nextInt(SymbolKind.values().length);
            int _plus = (_nextInt + 1);
            it.setKind(SymbolKind.forValue(_plus));
            Location _location = new Location();
            final Procedure1<Location> _function_1 = (Location it_1) -> {
              it_1.setUri(PerformanceMeasurement.this.randomString());
              Range _range = new Range();
              final Procedure1<Range> _function_2 = (Range it_2) -> {
                int _nextInt_1 = PerformanceMeasurement.this.random.nextInt(100);
                int _nextInt_2 = PerformanceMeasurement.this.random.nextInt(100);
                Position _position = new Position(_nextInt_1, _nextInt_2);
                it_2.setStart(_position);
                int _nextInt_3 = PerformanceMeasurement.this.random.nextInt(100);
                int _nextInt_4 = PerformanceMeasurement.this.random.nextInt(100);
                Position _position_1 = new Position(_nextInt_3, _nextInt_4);
                it_2.setEnd(_position_1);
              };
              Range _doubleArrow = ObjectExtensions.<Range>operator_doubleArrow(_range, _function_2);
              it_1.setRange(_doubleArrow);
            };
            Location _doubleArrow = ObjectExtensions.<Location>operator_doubleArrow(_location, _function_1);
            it.setLocation(_doubleArrow);
          };
          SymbolInformation _doubleArrow = ObjectExtensions.<SymbolInformation>operator_doubleArrow(_symbolInformation, _function);
          this.data.add(_doubleArrow);
        }
        Map<String, JsonRpcMethod> _emptyMap = CollectionLiterals.<String, JsonRpcMethod>emptyMap();
        this.gson = new MessageJsonHandler(_emptyMap).getGson();
      }
      
      @Override
      public void run() {
        this.gson.toJson(this.data);
      }
    };
    this.measure(___PerformanceMeasurement_1, "measureSymbolInformation");
  }
  
  private String measure(final PerformanceMeasurement.Measurement measurement, final String name) {
    String _xblockexpression = null;
    {
      measurement.prepare();
      measurement.run();
      final long startTime = System.nanoTime();
      for (int i = 0; (i < 10); i++) {
        measurement.run();
      }
      final long endTime = System.nanoTime();
      final double duration = ((endTime - startTime) * 1e-7);
      _xblockexpression = InputOutput.<String>println((((name + ": ") + Double.valueOf(duration)) + " ms"));
    }
    return _xblockexpression;
  }
  
  private String randomString() {
    int _nextInt = this.random.nextInt(30);
    final int length = (_nextInt + 2);
    final StringBuilder builder = new StringBuilder(length);
    for (int i = 0; (i < length); i++) {
      {
        int _nextInt_1 = this.random.nextInt(75);
        int _plus = (_nextInt_1 + 48);
        final char c = ((char) _plus);
        builder.append(c);
      }
    }
    return builder.toString();
  }
}
